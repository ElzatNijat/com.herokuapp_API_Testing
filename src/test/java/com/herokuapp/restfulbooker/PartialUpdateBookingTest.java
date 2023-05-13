package com.herokuapp.restfulbooker;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PartialUpdateBookingTest extends BaseTest {

	@Test
	public void partialUpdateBookingTest() {
		// create booking
		Response responseCreate = createBooking();
		responseCreate.print();
		
		// get bookingID of new booking
		int bookingid = responseCreate.jsonPath().getInt("bookingid");
		
		// Create JSON body
				JSONObject body = new JSONObject();
				body.put("firstname", "Alex");
			
				JSONObject bookingdates = new JSONObject();
				bookingdates.put("checkin", "2023-08-15");
				bookingdates.put("checkout", "2023-08-25");
				
				body.put("bookingdates", bookingdates);

		// PartialUpdate booking
				Response responseUpdate = RestAssured.given(spec).auth().preemptive().basic("admin", "password123").contentType(ContentType.JSON).body(body.toString())
						.patch("/booking/" + bookingid);
				responseUpdate.print();
				
			//	 username: "admin"
			//	 password: "password123"
				
		// Verifications
		// Verfiy response 200
		Assert.assertEquals(responseUpdate.getStatusCode(), 200, "Status code should be 200, but it's not");

		// Verify all fields
		SoftAssert softAssert = new SoftAssert();

		String actualFirstName = responseUpdate.jsonPath().getString("firstname");
		softAssert.assertEquals(actualFirstName, "Alex", "firstname in response is not expected");

		String actualLastName = responseUpdate.jsonPath().getString("lastname");
		softAssert.assertEquals(actualLastName, "Romanov", "lastname in response is not expected");

		int price = responseUpdate.jsonPath().getInt("totalprice");
		softAssert.assertEquals(price, 250, "total price in response is not expected");

		boolean depositpaid = responseUpdate.jsonPath().getBoolean("depositpaid");
		softAssert.assertFalse(depositpaid, "deposit paid should be false, but it's not");

		String actualCheckIn = responseUpdate.jsonPath().getString("bookingdates.checkin");
		softAssert.assertEquals(actualCheckIn, "2023-08-15", "checkin in response is not expected");

		String actualCheckOut = responseUpdate.jsonPath().getString("bookingdates.checkout");
		softAssert.assertEquals(actualCheckOut, "2023-08-25", "checkout in response is not expected");

		String actualAdditionalneeds = responseUpdate.jsonPath().getString("additionalneeds");
		softAssert.assertEquals(actualAdditionalneeds, "Baby crib", "additionalneeds in response is not expected");
		
		softAssert.assertAll();

	}

	

}
