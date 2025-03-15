package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiService {

    private static final Logger log = LoggerFactory.getLogger(ApiService.class);

    public static RequestSpecification prepareRequest() {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        return request;
    }

    public static Response sendRequest(String method, String endpoint, RequestSpecification request) {
        log.info("Executing {} request to {}", method,  endpoint);
        switch (method.toUpperCase()) {
            case "POST":
                return request.post( endpoint);
            case "GET":
                return request.get( endpoint);
            case "PUT":
                return request.put( endpoint);
            case "DELETE":
                return request.delete( endpoint);
            default:
                throw new IllegalArgumentException("Invalid HTTP method: " + method);
        }

    }
}
