package com.bridgelabz.fundoonotes.restassuredtest;

import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;

public class UserServiceRestAssuredApiTest {
	
	/* Used Objects */
	private String baseURL = "http://localhost:8080";
	private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoibXNzb25hcjI2QGdtYWlsLmNvbSJ9.PnmJiMaZVOJ03T5WgZU8k0VNEK-Osgj-mCtWe2whkUQ";
	private String userId = "5df087f657013002a4d298cf";
	
	
	/**
	 * @purpose Find User Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void findUserApiTest() {

		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		httpRequest.header("token", token);
		Response response = httpRequest.request(Method.GET, "/user");
		int code = response.getStatusCode();
		System.out.println("Get User Status Code: " + code);
		assertEquals(200, code);
	}
	
	
	/**
	 * @purpose Show All Users Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void showUsersApiTest() {

		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		Response response = httpRequest.request(Method.GET,"/showusers");
		int code = response.getStatusCode();
		System.out.println("Get User Status Code: "+code);
		assertEquals(200,code);
		
	}
	
	
	/**
	 * @purpose Login User Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void loginApiTest() {
		
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		JSONObject logindto = new JSONObject();
		logindto.put("email","mssonar26@gmail.com");
		logindto.put("password","123456789");
		httpRequest.body(logindto.toJSONString());
		httpRequest.header("Content-Type","application/json");
		httpRequest.header("token", token);

		Response response = httpRequest.request(Method.POST,"/login");
		
		int code = response.getStatusCode();
		System.out.println("Get User Status Code: "+code);		
		assertEquals(200,code);
	}
	
	
	/**
	 * @purpose Forget User Password Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void forgetPasswordTest() {
	
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		JSONObject forgetdto = new JSONObject();
		forgetdto.put("email","mssonar26@gmail.com");
		httpRequest.body(forgetdto.toJSONString());
		httpRequest.header("Content-Type","application/json");
		Response response = httpRequest.request(Method.POST, "/forget");
		
		int code = response.getStatusCode();
		System.out.println("Get User Status Code: "+code);		
		assertEquals(200,code);
	}
	
	
	/**
	 * @purpose Reset User Password Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void resetPasswordTest() {

		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		JSONObject resetdto = new JSONObject();
		resetdto.put("newPassword","12345678");
		resetdto.put("confirmPassword","12345678");
		httpRequest.header("Content-Type","application/json");
		httpRequest.header("token", token);
		httpRequest.body(resetdto.toJSONString());
		
		Response response = httpRequest.request(Method.POST,"/reset");
		int code = response.getStatusCode();
		System.out.println("Get User Status Code: "+code);	
		assertEquals(200, code);
	}
	
	
	/**
	 * @purpose Delete User Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
    public void deleteUserApiTest() {
		
        RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
        httpRequest.header("Content-Type","application/json");
        httpRequest.header("id", userId);

        Response response = httpRequest.request(Method.DELETE, "/deleteuser");
        int code = response.getStatusCode();
        System.out.println("response code::"+code);
        assertEquals(200,response.getStatusCode());    
        
    }
	
	
	/**
	 * @purpose Add User Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void addUserApiTest() {
		
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		JSONObject regdto = new JSONObject();
		regdto.put("firstName","Mayuresh");
		regdto.put("lastName","Sonar");
		regdto.put("email","mssonar26@gmail.com");
		regdto.put("password","123345678");
		regdto.put("confirmPassword","123345678");
		regdto.put("mobileNumber","9735409584");
		httpRequest.body(regdto.toJSONString());
		httpRequest.header("Content-Type","application/json");
		
		Response response = httpRequest.request(Method.POST,"/createuser");
		System.out.println("User Status Code "+response.getStatusCode());
		assertEquals(200, response.getStatusCode());
	}
	
	
	/**
	 * @purpose Update User Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void updateUserApiTest() {
		
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		JSONObject updatedto = new JSONObject();
		updatedto.put("firstName","Mayuresh");
		updatedto.put("lastName","Sonar");
		updatedto.put("mobileNumber","9735409584");
		httpRequest.body(updatedto.toJSONString());
		httpRequest.header("Content-Type","application/json");
		httpRequest.header("token", token);
		
		Response response = httpRequest.request(Method.PUT, "/updateuser");
		System.out.println("User Status Code "+response.getStatusCode());
		assertEquals(200, response.getStatusCode());
	}
}
