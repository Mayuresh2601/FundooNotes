package com.bridgelabz.fundoonotes.restassuredtest;

import static org.junit.Assert.assertEquals;
import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;

public class NoteServiceRestAssuredApiTest {

    /* Used Parameters */
	private String baseURL = "http://localhost:8080";
	private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoiZGVtby5tYXl1cmVzaEBnbWFpbC5jb20ifQ.Yoym2VKSjnf4v2LdxGMln9eKdpG8CUUm9VQOILNjBlA";
	private String noteId = "5df0880f57013002a4d298d1";
	private String deletenoteId = "5df1e5235c56e5614628cbc2";
	private String collaboratorEmail = "mssonar26@gmail.com";
  
	
	/**
	 * @purpose Add Note Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void addNoteApiTest() {
		
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		JSONObject notedto = new JSONObject();
		notedto.put("title","Note Assured");
		notedto.put("description","Note Assured Implemented");
		httpRequest.header("Content-Type","application/json");
		httpRequest.body(notedto.toJSONString());	
		httpRequest.header("token", token);
	
		Response response =httpRequest.request(Method.POST, "/createnote");
		System.out.println(response.getStatusCode());
		assertEquals(response.getStatusCode(), 200);	
	}
	
	
	/**
	 * @purpose Update Note Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void updateNoteApiTest() {
		
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		JSONObject notedto = new JSONObject();
		notedto.put("title","Note Assured Api Test");
		notedto.put("description","Note Assured Implemented Successfully");
		httpRequest.header("Content-Type","application/json");
		httpRequest.body(notedto.toJSONString());
		httpRequest.header("id", noteId);
		httpRequest.header("token", token);
		
		Response response = httpRequest.request(Method.PUT, "/updatenote");
		assertEquals(response.getStatusCode(), 200);
	}
	
	
	/**
	 * @purpose Delete Note Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void deleteNoteApiTest() {
		
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
        httpRequest.header("token", token);
      	httpRequest.header("id", deletenoteId);

		Response response = httpRequest.request(Method.DELETE,"/deletenote");
		assertEquals(response.getStatusCode(), 200);
	}
	
	
	/**
	 * @purpose Pin/Unpin Note Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void isPinApiTest() {
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
	    httpRequest.header("token", token);
      	httpRequest.header("id", noteId);
      	
		Response response = httpRequest.request(Method.PUT, "/notes/pin");
		assertEquals(response.getStatusCode(), 200);
	}
	
	
	/**
	 * @purpose Trash/Recover Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void isTrashApiTest() {
		
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		httpRequest.header("token", token);
      	httpRequest.header("id", noteId);
      	
		Response response = httpRequest.request(Method.PUT,"/notes/trash");
		assertEquals(response.getStatusCode(), 200);
	}
	
	
	/**
	 * @purpose Archive/Unarchive Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void archiveApiTest() {
		
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		httpRequest.header("token", token);
      	httpRequest.header("id", noteId);
      	
		Response response = httpRequest.request(Method.PUT,"/notes/archieve");
		assertEquals(response.getStatusCode(), 200);
	}
	
	
	/**
	 * @purpose Find Note Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void findNoteApiTest() {

		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		httpRequest.header("token", token);
		httpRequest.header("id", noteId);
		
		Response response = httpRequest.request(Method.GET, "/notes");
		int code = response.getStatusCode();
		System.out.println("Get User Status Code: " + code);
		assertEquals(200, code);
	}
	
	
	/**
	 * @purpose Show All Notes Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void showNotesApiTest() {

		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		
		Response response = httpRequest.request(Method.GET, "/shownotes");
		int code = response.getStatusCode();
		System.out.println("Get User Status Code: "+code);
		assertEquals(200, code);
	}
	
	
	/**
	 * @purpose Sort All Notes by Title in Ascending Order Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void sortNoteByTitleAscApiTest() {

		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		
		Response response = httpRequest.request(Method.GET, "/sortbytitleasc");
		int code = response.getStatusCode();
		System.out.println("Get User Status Code: "+code);
		assertEquals(200, code);
	}
	
	
	/**
	 * @purpose Sort All Notes by Title in Descending Order Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void sortNoteByTitleDescApiTest() {

		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		
		Response response = httpRequest.request(Method.GET, "/sortbytitledesc");
		int code = response.getStatusCode();
		System.out.println("Get User Status Code: "+code);
		assertEquals(200, code);
	}
	
	
	/**
	 * @purpose Sort All Notes by Title in Ascending Order Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void sortNoteByDateAscApiTest() {

		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		
		Response response = httpRequest.request(Method.GET, "/sortbydateasc");
		int code = response.getStatusCode();
		System.out.println("Get User Status Code: "+code);
		assertEquals(200, code);
	}
	
	
	/**
	 * @purpose Sort All Notes by Title in Ascending Order Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void sortNoteByDateDescApiTest() {

		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		
		Response response = httpRequest.request(Method.GET, "/sortbydatedesc");
		int code = response.getStatusCode();
		System.out.println("Get User Status Code: "+code);
		assertEquals(200, code);
	}
	
	
	/**
	 * @purpose Add Collaborator to Note Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void addCollaboratorApiTest() {
		
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		
		JSONObject collaboratordto = new JSONObject();
		collaboratordto.put("collaboratorEmail","mssonar26@gmail.com");
		httpRequest.header("Content-Type","application/json");
		httpRequest.body(collaboratordto.toJSONString());
		httpRequest.header("id", noteId);
		httpRequest.header("token", token);
		
		Response response = httpRequest.request(Method.POST, "/addcollaborator");
		assertEquals(response.getStatusCode(), 200);
	}
	
	
	/**
	 * @purpose Remove Collaborator from Note Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void removeCollaboratorApiTest() {
		
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		
		httpRequest.header("id", noteId);
		httpRequest.header("token", token);
		httpRequest.header("collaboratorEmail", collaboratorEmail);
		
		Response response = httpRequest.request(Method.DELETE, "/removecollaborator");
		assertEquals(response.getStatusCode(), 200);
	}
	
	
	/**
	 * @purpose Add Reminder to Note Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void addReminderApiTest() {
		
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		
		httpRequest.header("id", noteId);
		httpRequest.header("token", token);
		
		Response response = httpRequest.request(Method.POST, "/addreminder?year=2019&month=12&day=31&hour=24&minute=00&second=00");
		assertEquals(response.getStatusCode(), 200);
	}
	
	
	/**
	 * @purpose Edit Reminder to Note Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void editReminderApiTest() {
		
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		
		httpRequest.header("id", noteId);
		httpRequest.header("token", token);
		
		Response response = httpRequest.request(Method.POST, "/addreminder?year=2019&month=12&day=31&hour=23&minute=55&second=00");
		assertEquals(response.getStatusCode(), 200);
	}
	
	
	/**
	 * @purpose Remove Reminder from Note Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void removeReminderApiTest() {
		
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseURL);
		
		httpRequest.header("id", noteId);
		httpRequest.header("token", token);
		
		Response response = httpRequest.request(Method.DELETE, "/removereminder");
		assertEquals(response.getStatusCode(), 200);
	}
}
