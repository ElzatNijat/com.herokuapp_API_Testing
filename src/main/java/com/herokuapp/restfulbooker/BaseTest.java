package com.herokuapp.restfulbooker;

import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseTest {
	protected RequestSpecification spec;
	
	@BeforeMethod
	public void setUp() {
		spec = new RequestSpecBuilder().
				setBaseUri("https://restful-booker.herokuapp.com").
				build();
	}
	
// https://restful-booker.herokuapp.com/apidoc/index.html#api-Booking-GetBookings
	
	protected Response createBooking() {
		// Create JSON body
		JSONObject body = new JSONObject();
		body.put("firstname", "Dmitry");
		body.put("lastname", "Romanov");
		body.put("totalprice", 250);
		body.put("depositpaid", false);

		JSONObject bookingdates = new JSONObject();
		bookingdates.put("checkin", "2023-05-01");
		bookingdates.put("checkout", "2023-05-25");

		body.put("bookingdates", bookingdates);
		body.put("additionalneeds", "Baby crib");

		// Get response
		Response response = RestAssured.given(spec).contentType(ContentType.JSON).body(body.toString())
				.post("/booking");
		return response;
	}

}
