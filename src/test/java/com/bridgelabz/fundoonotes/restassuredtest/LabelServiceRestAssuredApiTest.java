package com.bridgelabz.fundoonotes.restassuredtest;

import static org.junit.Assert.assertEquals;
import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;

public class LabelServiceRestAssuredApiTest {

	/* Used Parameters */
	private String baseURL = "http://localhost:8080";
	private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoiZGVtby5tYXl1cmVzaEBnbWFpbC5jb20ifQ.Yoym2VKSjnf4v2LdxGMln9eKdpG8CUUm9VQOILNjBlA";
	private String noteId = "5df0880f57013002a4d298d1";
	private String labelId = "5df088a357013002a4d298d4";
	private String deletelabelId = "5df1f0655c56e5614628cbcd";

	
	/**
	 * @purpose Add Label Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void addLabelApiTest() {
		
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		JSONObject labeldto = new JSONObject();
		labeldto.put("labelTitle","Important Notes");
		httpRequest.body(labeldto.toJSONString());
		httpRequest.header("Content-Type","application/json");
		httpRequest.header("token", token);
		
		Response response = httpRequest.request(Method.POST, "/createlabel");
		assertEquals(response.getStatusCode(),200);
	}
	
	
	/**
	 * @purpose Update Label Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void updateLabelApiTest() {
		
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		JSONObject labeldto = new JSONObject();
		labeldto.put("labelTitle","Very Important Notes");
		httpRequest.body(labeldto.toJSONString());
		httpRequest.header("Content-Type","application/json");
		httpRequest.header("labelid", labelId);
		httpRequest.header("token", token);
	
		Response response = httpRequest.request(Method.PUT, "/updatelabel");
		assertEquals(response.getStatusCode(),200);
	}
	
	
	/**
	 * @purpose Delete Label Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void deleteLabelApiTest() {
		
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
        httpRequest.header("token", token);
      	httpRequest.header("labelid", deletelabelId);

		Response response = httpRequest.request(Method.DELETE, "/deletelabel");
		assertEquals(response.getStatusCode(), 200);
	}
	
	
	/**
	 * @purpose Find Label By Id Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void findLabelByIdApiTest() {
		
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		httpRequest.header("token", token);
		httpRequest.header("labelid", labelId);
		
		Response response = httpRequest.request(Method.GET, "/labels");
		int code = response.getStatusCode();
		System.out.println("Get User Status Code: " + code);
		assertEquals(200, code);
	}
	
	
	/**
	 * @purpose Show All Labels Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void showLabelsApiTest() {

		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		
		Response response = httpRequest.request(Method.GET, "/showlabels");
		int code = response.getStatusCode();
		System.out.println("Get User Status Code: "+code);
		assertEquals(200, code);
		
	}
	
	
	/**
	 * @purpose Add Label to Note Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void addLabelToNoteApiTest() {
		
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		httpRequest.header("noteid", noteId);
		httpRequest.header("labelid", labelId);
		httpRequest.header("token", token);
		
		Response response = httpRequest.request(Method.POST, "/addlabeltonote");
		assertEquals(response.getStatusCode(),200);
	}
}
