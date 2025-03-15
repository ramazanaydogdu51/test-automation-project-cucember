package utils;

import io.qameta.allure.Allure;

import io.qameta.allure.AllureLifecycle;
import io.restassured.response.Response;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import static io.qameta.allure.Allure.getLifecycle;


public class CommonLibApi {
    private static final Logger log = LogManager.getLogger(CommonLibApi.class);
    static JsonObject requestBody;
    RequestSpecification request;
    private static final AllureLifecycle lifecycle = getLifecycle();



    public static JsonObject setRequestBodyFromJsonFile(RequestSpecification request, String fileName) {
        String filePath = "src/test/resources/data/" + fileName;
        log.info("📄 Loading JSON request body from file: {}", filePath);
        Allure.step("📄 Loading JSON request body from file: "+ filePath);

        JsonObject requestBody = JsonReader.readJsonFile(filePath);  // JSON dosyasını oku
        request.body(requestBody.toString());  // Request'e ekle
        log.info("✅ Request body set successfully: {}", requestBody);
        Allure.step("✅ Request body set successfully: "+requestBody+"");
        return requestBody;  // JSON nesnesini return et
    }


    public static void modifyRequestBodyField(RequestSpecification request, JsonObject requestBody, String field, String newValue) {
        if (requestBody != null && requestBody.has(field)) {
            log.info("Modifying field '{}' with new value '{}'", field, newValue);
            Allure.step("Modifying field '{"+field+"}' with new value '{"+newValue+"}'");

            // JSON nesnesindeki alanı güncelle
            requestBody.addProperty(field, newValue);

            // Güncellenmiş JSON'u isteğe ekle
            request.body(requestBody.toString());

            log.info("Updated request body: {}", requestBody);
            Allure.step("Updated request body: {"+requestBody+"}");
        } else {
            log.warn("⚠️ Field '{}' not found in request body or requestBody is null!", field);
            Allure.step("⚠️ Field '{"+field+"}' not found in request body or requestBody is null!" );
        }
    }


    public static void validateStatusCode(Response response, int expectedStatusCode) {
        log.info("Actual Status Code {} ",response.getStatusCode());
        Allure.step("Actual Status Code {"+response.getStatusCode()+"}");
        Assert.assertEquals(   response.getStatusCode(),expectedStatusCode,"Expected HTTP status code: "+expectedStatusCode);
    }

    public static String extractJsonValue(Response response, String key) {
        JsonObject jsonResponse = JsonParser.parseString(response.getBody().asString()).getAsJsonObject();
        return jsonResponse.get(key).getAsString();
    }

    public static void validateResponseData(Response response, String key, String expectedValue) {
        JsonObject jsonResponse = JsonParser.parseString(response.getBody().asString()).getAsJsonObject();
        log.info("Validating response: Expected {} = {}, Actual = {}", key, expectedValue, jsonResponse.get(key).getAsString());
        Allure.step("Validating response: Expected {"+key+"} = {"+expectedValue+"},"+" Actual = {"+jsonResponse.get(key).getAsString()+"}" );
        Assert.assertEquals(  jsonResponse.get(key).getAsString(),expectedValue,key+" = does not match");
    }
    public static void verifyFieldExists(Response response, String field) {
        JsonObject jsonResponse = JsonParser.parseString(response.getBody().asString()).getAsJsonObject();

        // Check if the field exists
        Assert.assertTrue(jsonResponse.has(field), "❌ Field '" + field + "' is missing in the response!");

        log.info("✅ Verified that the response contains the field: {}", field);
        Allure.step("✅ Verified that the response contains the field: {"+field+"}");
    }



    public static Response sendApiRequest(String method, String endpoint, RequestSpecification request) {
        log.info("Sending {} request to {}", method, endpoint);
        Allure.step("Sending {"+method+"} request to {"+endpoint+"}");

        Response response = ApiService.sendRequest(method, endpoint, request);

        log.info("Endpoint: {}", endpoint);
        Allure.step("Endpoint: {"+endpoint+"}");

        log.info("HTTP Method: {}", method);
        Allure.step("HTTP Method: {"+method+"}");

        log.info("Response Time: {} ms", response.getTime());
        Allure.step("Response Time: {"+response.getTime()+"} ms");

        log.info("HTTP Status Code: {}", response.getStatusCode());
        Allure.step("HTTP Status Code: {"+response.getStatusCode()+"}");

        log.info("Response Body: {}", response.asPrettyString());
        Allure.step("Response Body: "+response.asPrettyString()+"");

        return response;
    }


}
