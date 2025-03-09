package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ApiSteps {
    private static final String BASE_URL = "https://reqres.in/api/users";
    private static Response response;
    private static String userId;

    private static final Logger log = LogManager.getLogger(ApiSteps.class);

    @Given("I send a POST request to create a user with name {string} and job {string}")
    public void createUser(String name, String job) {
        log.info("Creating a user with name: {} and job: {}", name, job);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("name", name);
        requestBody.addProperty("job", job);

        response = request.body(requestBody.toString()).post(BASE_URL);
        log.info("Response: {}", response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 201);

        JsonObject jsonResponse = JsonParser.parseString(response.getBody().asString()).getAsJsonObject();
        userId = jsonResponse.get("id").getAsString();
        log.info("Created User ID: {}", userId);
    }

    @Then("the response status code should be {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        log.info("Verifying response status code: Expected = {}, Actual = {}", expectedStatusCode, response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
    }

    @Given("I send a PUT request to update user with ID 2 to name {string} and job {string}")
    public void updateUser(String name, String job) {
        log.info("Updating user with ID: 2 to name: {} and job: {}", name, job);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("name", name);
        requestBody.addProperty("job", job);

        response = request.body(requestBody.toString()).put(BASE_URL + "/2");
        log.info("Response: {}", response.getBody().asString());
    }

    @Then("I should see the updated user details")
    public void verifyUpdatedUserDetails() {
        log.info("Verifying updated user details...");

        JsonObject jsonResponse = JsonParser.parseString(response.getBody().asString()).getAsJsonObject();
        log.info("Updated Name: {}", jsonResponse.get("name").getAsString());
        log.info("Updated Job: {}", jsonResponse.get("job").getAsString());

        Assert.assertEquals(jsonResponse.get("name").getAsString(), "Mike");
        Assert.assertEquals(jsonResponse.get("job").getAsString(), "Manager");
    }

    @Given("I send a DELETE request to remove user with ID 2")
    public void deleteUser() {
        log.info("Deleting user with ID: 2");
        response = RestAssured.delete(BASE_URL + "/2");
        log.info("Response status: {}", response.getStatusCode());
    }

    @Given("I create a new user with name {string} and job {string}")
    public void createDelayedUser(String name, String job) {
        createUser(name, job);
    }

    @When("I send a request to get user details with a 5-second delay")
    public void getUserWithDelay() {
        log.info("Fetching user details with a 5-second delay...");
        response = RestAssured.get(BASE_URL + "/" + userId + "?delay=5");
        log.info("Response: {}", response.getBody().asString());
    }

    @Then("I should see the user details in the response")
    public void verifyUserDetails() {
        log.info("Verifying user details in delayed response...");

        JsonObject jsonResponse = JsonParser.parseString(response.getBody().asString()).getAsJsonObject();
        log.info("User ID from response: {}", jsonResponse.get("id").getAsString());

        Assert.assertEquals(jsonResponse.get("id").getAsString(), userId);
    }
}
