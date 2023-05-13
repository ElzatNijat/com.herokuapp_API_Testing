package com.herokuapp.restfulbooker;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;

public class GetBookingTests extends BaseTest {

	// https://restful-booker.herokuapp.com/apidoc/index.html#api-Booking-GetBookings

	@Test(enabled = false)
	public void getBookingTest() {
		
		// create booking
		Response responseCreate = createBooking();
		responseCreate.print();

		// get bookingID of new booking
		int bookingid = responseCreate.jsonPath().getInt("bookingid");

		// Set path parameter
		spec.pathParam("bookingId", bookingid);
		
		// Get response with booking IDs
		Response response = RestAssured.given(spec).get("/booking/{bookingId}");
		response.print();

		// Verfiy response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");

		// Verify all fields
		SoftAssert softAssert = new SoftAssert();

		String actualFirstName = response.jsonPath().getString("firstname");
		softAssert.assertEquals(actualFirstName, "Dmitry", "firstname in response is not expected");

		String actualLastName = response.jsonPath().getString("lastname");
		softAssert.assertEquals(actualLastName, "Romanov", "lastname in response is not expected");

		int price = response.jsonPath().getInt("totalprice");
		softAssert.assertEquals(price, 250, "total price in response is not expected");

		boolean depositpaid = response.jsonPath().getBoolean("depositpaid");
		softAssert.assertFalse(depositpaid, "deposit paid should be false, but it's not");

		String actualCheckIn = response.jsonPath().getString("bookingdates.checkin");
		softAssert.assertEquals(actualCheckIn, "2023-05-01", "checkin in response is not expected");

		String actualCheckOut = response.jsonPath().getString("bookingdates.checkout");
		softAssert.assertEquals(actualCheckOut, "2023-05-25", "checkout in response is not expected");

		String actualAdditionalneeds = response.jsonPath().getString("additionalneeds");
		softAssert.assertEquals(actualAdditionalneeds, "Baby crib", "additionalneeds in response is not expected");
		
		softAssert.assertAll();

	}
	
	@Test
	public void getBookingXMLTest() {
		
		// create booking
		Response responseCreate = createBooking();
		responseCreate.print();

		// get bookingID of new booking
		int bookingid = responseCreate.jsonPath().getInt("bookingid");

		// Set path parameter
		spec.pathParam("bookingId", bookingid);
		
		// Get response with booking IDs
		Header xml = new Header("Accept", "application/xml");
		spec.header(xml);
		Response response = RestAssured.given(spec).get("/booking/{bookingId}");
		response.print();

		// Verfiy response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");

		// Verify all fields
		SoftAssert softAssert = new SoftAssert();

		String actualFirstName = response.xmlPath().getString("booking.firstname");
		softAssert.assertEquals(actualFirstName, "Dmitry", "firstname in response is not expected");

		String actualLastName = response.xmlPath().getString("booking.lastname");
		softAssert.assertEquals(actualLastName, "Romanov", "lastname in response is not expected");

		int price = response.xmlPath().getInt("booking.totalprice");
		softAssert.assertEquals(price, 250, "total price in response is not expected");

		boolean depositpaid = response.xmlPath().getBoolean("booking.depositpaid");
		softAssert.assertFalse(depositpaid, "deposit paid should be false, but it's not");

		String actualCheckIn = response.xmlPath().getString("booking.bookingdates.checkin");
		softAssert.assertEquals(actualCheckIn, "2023-05-01", "checkin in response is not expected");

		String actualCheckOut = response.xmlPath().getString("booking.bookingdates.checkout");
		softAssert.assertEquals(actualCheckOut, "2023-05-25", "checkout in response is not expected");

		String actualAdditionalneeds = response.xmlPath().getString("booking.additionalneeds");
		softAssert.assertEquals(actualAdditionalneeds, "Baby crib", "additionalneeds in response is not expected");
		
		softAssert.assertAll();

}
}