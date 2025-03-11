
@API @Regression
Feature: API Tests on Reqres
  @POST
  Scenario: Create a new user via API
    Given I send a POST request to create a user with name "John" and job "Tester"
    Then the response status code should be 201
    And I should see the created user's ID

  Scenario: Update an existing user via API
    Given I send a PUT request to update user with ID 2 to name "Mike" and job "Manager"
    Then the response status code should be 200
    And I should see the updated user details

  Scenario: Delete a user via API
    Given I send a DELETE request to remove user with ID 2
    Then the response status code should be 204

  Scenario: Validate delayed response from API
    Given I create a new user with name "Alice" and job "Developer"
    When I send a request to get user details with a 5-second delay
    Then the response status code should be 200
    And I should see the user details in the response
