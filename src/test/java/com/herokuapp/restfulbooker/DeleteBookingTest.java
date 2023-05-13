package com.herokuapp.restfulbooker;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DeleteBookingTest extends BaseTest {

	@Test
	public void deleteBookingTest() {
		// create booking
		Response responseCreate = createBooking();
		responseCreate.print();

		// get bookingID of new booking
		int bookingid = responseCreate.jsonPath().getInt("bookingid");

		// Delete booking
		Response responseDelete = RestAssured.given(spec).auth().preemptive().basic("admin", "password123")
				.delete("/booking/" + bookingid);
		responseDelete.print();

		// username: "admin"
		// password: "password123"

		// Verifications
		// Verfiy response 201
		Assert.assertEquals(responseDelete.getStatusCode(), 201, "Status code should be 201, but it's not");
		
		// extra verification
		Response responseGet = RestAssured.get("https://restful-booker.herokuapp.com/booking/" + bookingid);
		responseGet.print();
		
		Assert.assertEquals(responseGet.getBody().asString(), "Not Found", "Body should be 'Not Found', but it is not");
		
	}

}
