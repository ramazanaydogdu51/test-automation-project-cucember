#  Test Automation Project

This project is a **fully automated test framework** built with **Cucumber, Selenium WebDriver, RestAssured, JUnit, and Allure Reporting**.  
It is designed to **automate web application testing** using the **Page Object Model (POM)** and also supports **API testing**.


---
# 🎬 Test Case Execution

This section explains how to execute test cases. Below, you can find demonstration videos for both **Webdriver-based UI tests** and **API tests**.

## 🖥️ Webdriver Test Execution
Watch the video below to see how Web UI tests are executed:  
[![Webdriver Test Execution](https://img.youtube.com/vi/UQ-ZEWvgshs/0.jpg)](https://youtu.be/UQ-ZEWvgshs)

📌 **Description:** Demonstrates how to execute web-based test scenarios using Selenium Webdriver.

## 🌐 API Test Execution
Watch the video below to see how API tests are executed:  
[![API Test Execution](https://img.youtube.com/vi/IycbsaKa3r4/0.jpg)](https://youtu.be/IycbsaKa3r4)

📌 **Description:** Shows how to run API tests and integrate them with tools like Postman or RestAssured.

### 🎬 Allure Report Demonstration




### 🎬 Resources and Logs


## 📌 Features

- 🚀 **Cucumber & Gherkin Syntax** - BDD (Behavior-Driven Development)
- ✅ **Selenium WebDriver** - Cross-browser UI automation
- 🔄 **RestAssured** - API Testing with request validation
- ⚙️ **TestNG** - Advanced test execution with annotations
- 📊 **Allure Reports** - Detailed test reporting and analysis
- 📝 **Log4j2** - Advanced logging for debugging
- 🌍 **JSON-based test configuration** - Dynamic data management
- 🔥 **Parallel Execution Support** - Faster test execution


---
## 📜 Example Cucumber Scenario (amazon.feature)

```gherkin
Feature: Amazon Search Functionality

  Scenario: User searches for a product on Amazon
    Given User is on the Amazon homepage
    When User searches for "laptop"
    Then Search results should contain "laptop"

```


## 📂 Project Structure

```
📂 CucumberAmazonTest
├── 📂 .allure                                   # Allure test result reports
├── 📂 .idea                                     # IntelliJ IDEA project settings
├── 📂 allure-results                            # Allure test output files
├── 📂 logs                                      # Stores log files
├── 📂 src
│   ├── 📂 test
│   │   ├── 📂 java
│   │   │   ├── 📂 pages                         # Page Object Model (POM) classes
│   │   │   │   ├── ☕ HomePage.java
│   │   │   ├── 📂 runners                       # Test runners for different test types
│   │   │   │   ├── ☕ APITestRunner.java
│   │   │   │   ├── ☕ BaseTestRunner.java
│   │   │   │   ├── ☕ RegressionTestRunner.java
│   │   │   │   ├── ☕ TestRunner.java
│   │   │   │   ├── ☕ UITestRunner.java
│   │   │   ├── 📂 stepDefinitions               # Step Definitions for Cucumber
│   │   │   │   ├── ☕ AmazonSteps.java
│   │   │   │   ├── ☕ ApiSteps.java
│   │   │   ├── 📂 utils                         # Utility classes
│   │   │   │   ├── ☕ ApiCommonLib.java
│   │   │   │   ├── ☕ ApiService.java
│   │   │   │   ├── ☕ BaseTest.java
│   │   │   │   ├── ☕ CommonLibWeb.java
│   │   │   │   ├── ☕ DriverManager.java
│   │   │   │   ├── ☕ JsonReader.java
│   │   ├── 📂 resources                       # Configuration & Test Data
│   │   │   ├── 📂 config                      # Stores test environment configurations
│   │   │   │   ├── 📄 config.json
│   │   │   │   ├── 📄 locators.json
│   │   │   ├── 📂 data                        # Test user data
│   │   │   │   ├── 📄 userData.json
│   │   │   ├── 📂 features                    # Cucumber feature files
│   │   │   │   ├── 📄 amazon.feature
│   │   │   │   ├── 📄 api.feature
│   │   │   ├── 📄 log4j2.xml                  # Log4j2 configuration file
├── 📂 target                                  # Compiled test results
├── 📄 pom.xml                                 # Maven dependencies

```

---

## ⚙️ Setup Instructions

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/ramazanaydogdu51/test-automation-project-cucember.git
cd test-automation-project-cucember
```

### 2️⃣ Install Dependencies

```bash
mvn clean install
```

### 3️⃣ Run Tests

Run all tests:

```bash
mvn test
```
Run specific tests:

Run UI Tests:
```bash
mvn test -Dcucumber.filter.tags="@UITest"
```
Run API Tests:
```bash
mvn test -Dcucumber.filter.tags="@APITest"
```
Run Regression Tests
```bash
mvn test -Dcucumber.filter.tags="@RegressionTest"
```


### 4️⃣ Generate and View Test Reports
Generate and view test reports with **Allure**:

```bash
mvn allure:serve
```
---

## 📜 Configuration Files

- **config.json** → Contains URLs for the test application.
- **locators.json** → Stores XPath and CSS selectors for page elements.
- **log4j2.xml** → Manages logging levels and output locations.
- **userData.json** → Test data (e.g., usernames, passwords).

---

## 📊 Test Reports

To generate and view test reports, follow these steps:

1️⃣ **Run the tests first:**
```bash
mvn test
```

2️⃣ **Generate Allure reports:**
```bash
mvn allure:serve
```
3️⃣ Open the report in the browser.



📌 Test logs are saved in:



```
logs/test-log.log
```

```
📂 target/allure-results/   # Allure raw test results
📂 target/site/allure/      # HTML report
```

---

## 📌 Technologies Used

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)  
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)  
![Selenium](https://img.shields.io/badge/Selenium-43B02A?style=for-the-badge&logo=selenium&logoColor=white)  
![TestNG](https://img.shields.io/badge/TestNG-FF6F00?style=for-the-badge&logo=testng&logoColor=white)  
![Allure](https://img.shields.io/badge/Allure-0A0A0A?style=for-the-badge&logo=allure&logoColor=white)  
![Cucumber](https://img.shields.io/badge/Cucumber-23D96C?style=for-the-badge&logo=cucumber&logoColor=white)  
![RestAssured](https://img.shields.io/badge/RestAssured-005571?style=for-the-badge&logo=rest-assured&logoColor=white)  
![Log4j](https://img.shields.io/badge/Log4j-2C2C2C?style=for-the-badge&logo=apache&logoColor=white)  
![WebDriver Manager](https://img.shields.io/badge/WebDriver_Manager-0078D7?style=for-the-badge&logo=selenium&logoColor=white)



# 🎬 Test Case Execution

This section explains how to execute test cases. Below, you can find demonstration videos for both **Webdriver-based UI tests** and **API tests**.

## 🖥️ Webdriver Test Execution
Watch the video below to see how Web UI tests are executed:  
[![Webdriver Test Execution](https://img.youtube.com/vi/UQ-ZEWvgshs/0.jpg)](https://youtu.be/UQ-ZEWvgshs)

📌 **Description:** Demonstrates how to execute web-based test scenarios using Selenium Webdriver.

## 🌐 API Test Execution
Watch the video below to see how API tests are executed:  
[![API Test Execution](https://img.youtube.com/vi/IycbsaKa3r4/0.jpg)](https://youtu.be/IycbsaKa3r4)

📌 **Description:** Shows how to run API tests and integrate them with tools like Postman or RestAssured.

### 🎬 Allure Report Demonstration




### 🎬 Resources and Logs





For any questions or suggestions, feel free to reach out!



