package com.stepdefinetions;

import io.cucumber.core.gherkin.messages.internal.gherkin.internal.com.eclipsesource.json.JsonObject;
import io.cucumber.core.gherkin.messages.internal.gherkin.internal.com.eclipsesource.json.JsonValue;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import junit.framework.Assert;
import io.cucumber.java.en.Then;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

public class PetStoreStepDefinetions {

	private int petId;
	// private int resPetId;
	// private String baseURL = "https://petstore.swagger.io/";
	private Response response;
	private JSONObject requestBody;

	ResourceBundle routes = ResourceBundle.getBundle("routes");

	@Given("I have a valid pet ID")
	public void i_have_a_valid_pet_id() {
		petId = 11;
		// JSONObject jsonObj = new JSONObject(response.body().toString());
		// petId = jsonObj.getInt("id");
	}

	@When("I request the pet by ID")
	public void i_request_the_pet_by_id() {
		response = given().baseUri(routes.getString("baseURI")).accept("application/json").pathParam("petId", petId)
				.when().get(routes.getString("getPetEndPoint"));

	}

	@Then("the response status code should be {int}")
	public void the_response_status_code_should_be(int expectedStatusCode) {

		int actualStatusCode = response.getStatusCode();
		Assert.assertEquals(actualStatusCode, expectedStatusCode);
	}

	@Given("I have a new pet object with name {string} and ID {int}")
	public void i_have_a_new_pet_object_with_name_and_id(String name, Integer id) {
		// Create category JSON object
		JSONObject category = new JSONObject();
		category.put("id", id);
		category.put("name", name);

		// Create tags JSON array
		JSONObject tag = new JSONObject();
		tag.put("id", 100);
		tag.put("name", "Cat");
		JSONArray tags = new JSONArray();
		tags.put(tag);

		// Create photoUrls JSON array
		JSONArray photoUrls = new JSONArray();
		photoUrls.put("https://cat.img");

		// Create main JSON object
		requestBody = new JSONObject();
		requestBody.put("id", id);
		requestBody.put("category", category);
		requestBody.put("name", name);
		requestBody.put("photoUrls", photoUrls);
		requestBody.put("tags", tags);
		requestBody.put("status", "available");

		System.out.println(requestBody.toString());
	}

	@When("I add the new pet")
	public void i_add_the_new_pet() {
		response = given().accept("application/json").basePath("/v2").contentType("application/json")
				.body(requestBody.toString()).when()
				.post(routes.getString("baseURI") + routes.getString("postPetEndPoint"));
	}

	@Then("the response body should contain the details of the new pet")
	public void the_response_body_should_contain_the_details_of_the_new_pet() {
		response.then().body("id", equalTo(requestBody.get("id")));
		// Add more assertions as needed for other fields in the response body
	}

	@Given("I have an existing pet with ID {int}")
	public void i_have_an_existing_pet_with_id(int id) {
		petId = id;
		// Assume you have an existing pet with this ID in the system
	}

	@When("I delete the pet with ID {int}")
	public Response i_delete_the_pet_with_id(int id) {
		return response = given().baseUri(routes.getString("baseURI")).accept("application/json").pathParam("petId", id)
				.when().delete(routes.getString("deletePetEndPoint"));
	}

	@Then("the pet with ID {int} should not exist anymore")
	public void the_pet_with_id_should_not_exist_anymore(int id) {
		response = i_delete_the_pet_with_id(id);
		response.then().statusCode(404);
	}
}
