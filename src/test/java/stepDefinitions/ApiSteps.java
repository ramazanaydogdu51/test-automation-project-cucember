package stepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.gson.JsonObject;
import utils.ApiCommonLib;
import utils.ApiService;
import utils.JsonReader;

import static org.testng.Assert.assertNotNull;

public class ApiSteps {
    private static final String BASE_URL = "https://reqres.in/api/users";
    private static Response response;
    private static String userId;

    private static final Logger log = LogManager.getLogger(ApiSteps.class);
    private RequestSpecification request;
    private JsonObject requestBody;


//    @Given("I prepare a request for creating a user")
//    public void prepareUserRequest() {
//        log.info("Preparing request for user creation");
//        request = ApiService.prepareRequest();
//    }
    @Before
    public void prepareUserRequest() {
        log.info("Preparing request");
        request = ApiService.prepareRequest();
    }


    @When("I send a {string} request to {string}")
    public void sendRequest(String method, String endpoint) {
        response = ApiCommonLib.sendApiRequest(method, endpoint, request);


    }

    @Then("the response status code should be {int}")
    public void validateStatusCode(int expectedStatusCode) {
        ApiCommonLib.validateStatusCode(response, expectedStatusCode);
    }

        //I verify that the response has the "name" field equal to "Ramazan Aydogdu"
    @And("I verify that the response has the {string} field equal to {string}")
    public void validateResponseData(String key,String expectedValue) {
        ApiCommonLib.validateResponseData(response, key, expectedValue);
    }

    @Step ("I set the request body from file {string}")
    @And("I set the request body from file {string}")
    public void setRequestBodyFromFile(String fileName) {
//       String filePath = "src/test/resources/data/" + fileName; // Adjust path
//       log.info("Reading request body from file: {}", filePath);
//       requestBody = JsonReader.readJsonFile(filePath); // Store in requestBody
//       request.body(requestBody.toString());
//       log.info("Request body: {}", requestBody);
        requestBody = ApiCommonLib.setRequestBodyFromJsonFile(request, fileName);

    }

    @And("I modify the request body field {string} with value {string}")
    public void modifyRequestBodyField(String field, String newValue) {

        ApiCommonLib.modifyRequestBodyField(request, requestBody, field, newValue);

    }

//    @And("I modify the request body field {string} with value {string}")
//    public void modifyRequestBodyField(String field, String newValue) {
//        if (requestBody == null) {
//            log.error("❌ Request body is null! Make sure to set the request body first.");
//            throw new IllegalStateException("Request body is null. Call 'setRequestBodyFromFile' first.");
//        }
//
//        if (requestBody.has(field)) {
//            log.info("🔄 Modifying field '{}' with new value '{}'", field, newValue);
//
//            // Update JSON field
//            requestBody.addProperty(field, newValue);
//
//            // Ensure request is updated
//            request.body(requestBody.toString());
//
//            log.info("✅ Updated request body: {}", requestBody);
//        } else {
//            log.warn("⚠️ Field '{}' not found in request body!", field);
//            throw new IllegalArgumentException("Field '" + field + "' not found in request body.");
//        }
//    }


    @And("I verify that the response contains the field {string}")
    public void verifyFieldExistsInResponse(String field) {
        ApiCommonLib.verifyFieldExists(response, field);

    }




    //    @And("I set the request body with {string} {string} and {string} {string}")
//    public void setRequestBody(String firstKey ,String firstValue, String secondKey ,String secondValue) {
//        log.info("Setting request body with firstValue: {} and secondValue: {}", firstValue, secondValue);
//        requestBody = new JsonObject();
//        requestBody.addProperty(firstKey, firstValue);
//        requestBody.addProperty(secondKey, secondValue);
//        request.body(requestBody.toString());
//        log.info("Request body with firstValue: {} ", requestBody);
//    }


    //    @And("the response should contain the {string} {string} and {string} {string}")
//    public void validateResponseData(String firstKey,String expectedFirstValue,String secondKey, String expectedSecondValue) {
//        ApiCommonLib.validateResponseData(response, firstKey, expectedFirstValue,secondKey, expectedSecondValue);
//    }


}
