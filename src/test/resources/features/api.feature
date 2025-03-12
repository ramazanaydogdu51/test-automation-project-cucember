@API @Regression  @POST
Feature: API Tests on Reqres


  Scenario: Create a user successfully
    Given I prepare a request for creating a user
    And I set the request body with "name" "Ramazan" and "job" "Developer"
    When I send a "POST" request to "https://reqres.in/api/users"
    Then the response status code should be 201
    And the response should contain the user ID
    And the response should contain the "name" "Ramazan" and "job" "Developer"



  Scenario: Update a user successfully
    Given I prepare a request for creating a user
    And I set the request body with "name" "Ramazan" and "job" "Developer"
    When I send a "PUT" request to "https://reqres.in/api/users/0"
    Then the response status code should be 200
    And the response should contain the "name" "Ramazan" and "job" "Developer"


  Scenario: Delete a user successfully
    Given I prepare a request for creating a user
    When I send a "DELETE" request to "https://reqres.in/api/users/1"
    Then the response status code should be 204
