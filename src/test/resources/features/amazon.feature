
 @Regression
Feature: Amazon Web Testing


  Scenario: Open Amazon Home Page
    Given I open the "amazonhome" website
    Then I verify "amazonhome" website is correct
    Then I click "HomePage" "searchBox"
    Then I write "laptop" text
    Then I click "HomePage" "searchButton"
    Then I add product without discount
  #  Then I verify product is correct or not
  @UI
  Scenario: Open Amazon Home Page
    Given I open the "amazonhome" website
    Then I verify "amazonhome" website is correct
    Then I click "HomePage" "searchBox"
    Then I write "laptop" text
    Then I click "HomePage" "searchButton"
    Then I list all products on the page
    And I check for the first product that is not discounted
    And I add the selected product to the cart
    Then I verify the product is in the cart
    Then I delete product in the cart