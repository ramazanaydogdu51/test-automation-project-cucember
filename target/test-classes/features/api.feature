@APITest @RegressionTest
Feature: API Tests on Reqres


  Scenario: Create a user successfully
    And I set the request body from file "userData.json"
    And I modify the request body field "name" with value "Aydogdu"
    And I modify the request body field "job" with value "Developer in Test"
    When I send a "POST" request to "https://reqres.in/api/users"
    Then the response status code should be 201
    And I verify that the response has the "name" field equal to "Aydogdu"
    And I verify that the response has the "job" field equal to "Developer in Test"
    And I verify that the response contains the field "id"
    And I verify that the response contains the field "createdAt"


  Scenario: Update a user successfully
    And I set the request body from file "userData.json"
    And I modify the request body field "name" with value "Ramazan Aydogdu"
    And I modify the request body field "job" with value "Software"
    When I send a "PUT" request to "https://reqres.in/api/users/2"
    Then the response status code should be 200
    And I verify that the response has the "name" field equal to "Ramazan Aydogdu"
    And I verify that the response has the "job" field equal to "Software"
    And I verify that the response contains the field "updatedAt"


  Scenario: Delete a user successfully
    When I send a "DELETE" request to "https://reqres.in/api/users/2"
    Then the response status code should be 204



