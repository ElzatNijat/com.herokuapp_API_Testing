package com.herokuapp.restfulbooker;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetBookingIDsTests extends BaseTest {

	// https://restful-booker.herokuapp.com/apidoc/index.html#api-Booking-GetBookings

	@Test
	public void getBookingIdsWithoutFilterTest() {
		// Get response with booking IDs

		Response response = RestAssured.given(spec).get("/booking");
		response.print();

		// Verfiy response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");

		// Verify at least one booking ID in response
		List<Integer> bookingIDs = response.jsonPath().getList("bookingid");
		Assert.assertFalse(bookingIDs.isEmpty(), "List of bookingID is empty but it should not be");

	}

	@Test
	public void getBooknigIdsWithFilterTest() {
		// add query parameter to spec
		spec.queryParam("firstname", "Sally");
		spec.queryParam("lastname", "Brown");
		
		// Get response with booking IDs
		Response response = RestAssured.given(spec).get("/booking");
		response.print();

		// Verfiy response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");

		// Verify at least one booking ID in response
		List<Integer> bookingIDs = response.jsonPath().getList("bookingid");
		Assert.assertFalse(bookingIDs.isEmpty(), "List of bookingID is empty but it should not be");

	}

}
