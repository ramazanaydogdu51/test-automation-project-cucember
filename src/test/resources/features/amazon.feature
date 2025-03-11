
@UI @Regression
Feature: Amazon Web Testing


  Scenario: Open Amazon Home Page
    Given I open the "amazonhome" website
    Then I verify "amazonhome" website is correct
    Then I click "HomePage" "searchBox"
    Then I write "laptop" text
    Then I click "HomePage" "searchButton"
    Then I add product without discount