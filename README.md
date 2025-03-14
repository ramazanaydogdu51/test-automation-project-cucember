#  Test Automation Project

This project is a **fully automated test framework** built with **Cucumber, Selenium WebDriver, RestAssured, JUnit, and Allure Reporting**.  
It is designed to **automate web application testing** using the **Page Object Model (POM)** and also supports **API testing**.


---

## 📌 Features

- 🚀 **Cucumber & Gherkin Syntax** - BDD (Behavior-Driven Development)
- ✅ **Selenium WebDriver** - Cross-browser UI automation
- 🔄 **RestAssured** - API Testing with request validation
- ⚙️ **JUnit** - Structured test execution
- 📊 **Allure Reports** - Detailed test reporting and analysis
- 📝 **Log4j2** - Advanced logging for debugging
- 🌍 **JSON-based test configuration** - Dynamic data management
- 🔥 **Parallel Execution Support** - Faster test execution


---

## 📂 Project Structure

```
📂 test-automation-project
 ┣ 📂 .allure               # Allure test result reports
 ┣ 📂 logs                  # Stores log files
 ┃ ┗ 📜 test-log.log        # Log output file
 ┣ 📂 docs                  # GitHub Pages video hosting
 ┣ 📂 src
 ┃ ┣ 📂 main
 ┃ ┃ ┣ 📂 java
 ┃ ┃ ┃ ┣ 📂 utils           # Utility classes
 ┃ ┃ ┃ ┃ ┣ 📜 DriverManager  # Manages WebDriver instances
 ┃ ┃ ┃ ┃ ┗ 📜 JsonReader     # Reads test data from JSON files
 ┃ ┣ 📂 test
 ┃ ┃ ┣ 📂 java
 ┃ ┃ ┃ ┣ 📂 pages           # Page Object Model (POM) classes
 ┃ ┃ ┃ ┃ ┣ 📜 BasePage      # Base class for page interactions
 ┃ ┃ ┃ ┃ ┣ 📜 CareersPage   # Page object for Careers page
 ┃ ┃ ┃ ┃ ┣ 📜 HomePage      # Page object for Home page
 ┃ ┃ ┃ ┃ ┗ 📜 QAJobsPage    # Page object for QA jobs section
 ┃ ┃ ┃ ┣ 📂 tests           # Test cases
 ┃ ┃ ┃ ┃ ┗ 📜 Ramazan_Aydogdu_Test  # Main test suite
 ┃ ┃ ┃ ┣ 📂 utils           # Common utilities for tests
 ┃ ┃ ┃ ┃ ┗ 📜 CommonLib     # Shared utility methods
 ┃ ┃ ┃ ┣ 📂 resources       # Configuration & Test Data
 ┃ ┃ ┃ ┃ ┣ 📜 config.json   # Stores test environment configurations
 ┃ ┃ ┃ ┃ ┣ 📜 locators.json # Stores element locators (XPath, CSS)
 ┃ ┃ ┃ ┃ ┗ 📜 log4j2.xml    # Log4j2 configuration
 ┣ 📂 target                # Compiled test results
 ┣ 📜 .gitignore            # Git ignore settings
 ┣ 📜 pom.xml               # Maven dependencies
 ┣ 📜 README.md             # Project documentation
 ┗ 📜 testng.xml            # TestNG suite configuration
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

![Log4j](https://img.shields.io/badge/Log4j-2C2C2C?style=for-the-badge&logo=apache&logoColor=white)

![WebDriver Manager](https://img.shields.io/badge/WebDriver_Manager-0078D7?style=for-the-badge&logo=selenium&logoColor=white)


## 📹 Test Automation Videos

### 🎬 Test Case Execution
https://youtu.be/kXEGQ_RTeIA

[![Test Execution](https://img.youtube.com/vi/kXEGQ_RTeIA/0.jpg)](https://youtu.be/kXEGQ_RTeIA)

### 🎬 Allure Report Demonstration
https://youtu.be/99mPiVD2a_o

[![Allure Report](https://img.youtube.com/vi/99mPiVD2a_o/0.jpg)](https://youtu.be/99mPiVD2a_o)

### 🎬 Resources and Logs
https://youtu.be/Fhk5PlEZEBI

[![Resources and Logs](https://img.youtube.com/vi/Fhk5PlEZEBI/0.jpg)](https://youtu.be/Fhk5PlEZEBI)




For any questions or suggestions, feel free to reach out!



