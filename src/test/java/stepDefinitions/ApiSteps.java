package stepDefinitions;

import io.cucumber.java.en.And;
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
import utils.ApiCommonLib;
import utils.ApiService;

import static org.testng.Assert.assertNotNull;

public class ApiSteps {
    private static final String BASE_URL = "https://reqres.in/api/users";
    private static Response response;
    private static String userId;

    private static final Logger log = LogManager.getLogger(ApiSteps.class);
    private RequestSpecification request;
    private JsonObject requestBody;






    @Given("I prepare a request for creating a user")
    public void prepareUserRequest() {
        log.info("Preparing request for user creation");
        request = ApiService.prepareRequest();
    }

    @And("I set the request body with {string} {string} and {string} {string}")
    public void setRequestBody(String firstKey ,String firstValue, String secondKey ,String secondValue) {
        log.info("Setting request body with firstValue: {} and secondValue: {}", firstValue, secondValue);
        requestBody = new JsonObject();
        requestBody.addProperty(firstKey, firstValue);
        requestBody.addProperty(secondKey, secondValue);
        request.body(requestBody.toString());
        log.info("Request body with firstValue: {} ", requestBody);
    }

    @When("I send a {string} request to {string}")
    public void sendRequest(String method, String endpoint) {
        log.info("Sending {} request to {}", method, endpoint);
        // API çağrısını yap
        response = ApiService.sendRequest(method, endpoint, request);
        // Loglamalar
        log.info("Endpoint: {}", endpoint);
        log.info("HTTP Method: {}", method);
        log.info("Response Time: {} ms", response.getTime());
        log.info("HTTP Status Code: {}", response.getStatusCode());
        log.info("Response Body: {}", response.asPrettyString());
    }

    @Then("the response status code should be {int}")
    public void validateStatusCode(int expectedStatusCode) {
        ApiCommonLib.validateStatusCode(response, expectedStatusCode);
    }

    @And("the response should contain the user ID")
    public void validateUserIdInResponse() {
        String userId = ApiCommonLib.extractJsonValue(response, "id");
        assertNotNull( userId,"User ID should not be null");
        log.info("Created User ID: {}", userId);
    }

    @And("the response should contain the {string} {string} and {string} {string}")
    public void validateResponseData(String firstKey,String expectedFirstValue,String secondKey, String expectedSecondValue) {
        ApiCommonLib.validateResponseData(response, firstKey, expectedFirstValue,secondKey, expectedSecondValue);
    }


}
