package stepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.gson.JsonObject;
import utils.CommonLibApi;
import utils.ApiService;

import static org.testng.Assert.assertNotNull;

public class ApiSteps {
    private  final String BASE_URL = "https://reqres.in/api/users";
    private  Response response;
    private  String userId;

    private  final Logger log = LogManager.getLogger(ApiSteps.class);
    private RequestSpecification request;
    private JsonObject requestBody;



    @Before
    public void prepareUserRequest() {
        log.info("Preparing request");
        request = ApiService.prepareRequest();
    }


    @When("I send a {string} request to {string}")
    public void sendRequest(String method, String endpoint) {
        response = CommonLibApi.sendApiRequest(method, endpoint, request);


    }

    @Then("the response status code should be {int}")
    public void validateStatusCode(int expectedStatusCode) {
        CommonLibApi.validateStatusCode(response, expectedStatusCode);
    }

        //I verify that the response has the "name" field equal to "Ramazan Aydogdu"
    @And("I verify that the response has the {string} field equal to {string}")
    public void validateResponseData(String key,String expectedValue) {
        CommonLibApi.validateResponseData(response, key, expectedValue);
    }

    @Step ("I set the request body from file {string}")
    @And("I set the request body from file {string}")
    public void setRequestBodyFromFile(String fileName) {

        requestBody = CommonLibApi.setRequestBodyFromJsonFile(request, fileName);

    }

    @And("I modify the request body field {string} with value {string}")
    public void modifyRequestBodyField(String field, String newValue) {

        CommonLibApi.modifyRequestBodyField(request, requestBody, field, newValue);

    }

    @And("I verify that the response contains the field {string}")
    public void verifyFieldExistsInResponse(String field) {
        CommonLibApi.verifyFieldExists(response, field);

    }



}
