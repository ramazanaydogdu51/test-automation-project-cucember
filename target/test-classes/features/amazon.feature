
 @RegressionTest @UITest
Feature: Amazon Web Testing


  Scenario: Open Amazon Home Page
    Given I open the "amazonhome" website
    Then I verify "amazonhome" website is correct
    Then I click on the "HomePage" element on "searchBox" page
    Then I write "laptop" text
    Then I click on the "HomePage" element on "searchButton" page
    Then I list the "HomePage" on the "productList" page
    And I check for the first product that is not discounted
    And I add the selected product to the cart
    Then I verify the product is in the cart
    Then I click on the "HomePage" element on "deleteProduct" page