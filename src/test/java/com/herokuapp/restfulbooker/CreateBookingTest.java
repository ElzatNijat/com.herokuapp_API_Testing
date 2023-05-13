package com.herokuapp.restfulbooker;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateBookingTest extends BaseTest {

// https://restful-booker.herokuapp.com/apidoc/index.html#api-Booking-GetBookings

	@Test (enabled = false)
	public void createBookingTest() {

		Response response = createBooking();
		response.print();

		// Verifications
		// Verfiy response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");

		// Verify all fields
		SoftAssert softAssert = new SoftAssert();

		String actualFirstName = response.jsonPath().getString("booking.firstname");
		softAssert.assertEquals(actualFirstName, "Dmitry", "firstname in response is not expected");

		String actualLastName = response.jsonPath().getString("booking.lastname");
		softAssert.assertEquals(actualLastName, "Romanov", "lastname in response is not expected");

		int price = response.jsonPath().getInt("booking.totalprice");
		softAssert.assertEquals(price, 250, "total price in response is not expected");

		boolean depositpaid = response.jsonPath().getBoolean("booking.depositpaid");
		softAssert.assertFalse(depositpaid, "deposit paid should be false, but it's not");

		String actualCheckIn = response.jsonPath().getString("booking.bookingdates.checkin");
		softAssert.assertEquals(actualCheckIn, "2023-05-01", "checkin in response is not expected");

		String actualCheckOut = response.jsonPath().getString("booking.bookingdates.checkout");
		softAssert.assertEquals(actualCheckOut, "2023-05-25", "checkout in response is not expected");

		String actualAdditionalneeds = response.jsonPath().getString("booking.additionalneeds");
		softAssert.assertEquals(actualAdditionalneeds, "Baby crib", "additionalneeds in response is not expected");

		softAssert.assertAll();

	}

	@Test
	public void createBookingWithPOJOTest() {

		// Create body using POJOs
		Bookingdates bookingdates = new Bookingdates ("2023-05-01", "2023-05-25");
		Booking booking = new Booking("Olga", "Shyshkin", 200, false, bookingdates, "Baby crib");

		// Get response
		Response response = RestAssured.given(spec).contentType(ContentType.JSON).body(booking)
				.post("/booking");
		response.print();
		BookingID bookingid = response.as(BookingID.class);

		// Verifications
		// Verfiy response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");

		System.out.println("Request booking: " + booking.toString());
		System.out.println("Response booking: " + bookingid.getBooking().toString());
		
		// Verify all fields
		Assert.assertEquals(bookingid.getBooking().toString(), booking.toString());

	

	}

}
