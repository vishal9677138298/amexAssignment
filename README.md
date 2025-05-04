# Amex interview assessment

This project is a test automation framework using **Selenium WebDriver**, **Cucumber (BDD)**, and **Maven**, built in **Java**.

---

## 🛠️ Tools Used

- Java
- Maven
- Selenium WebDriver
- Cucumber (Gherkin)
- Spring context for dependency injection
- JUnit 5


---

## 🚀 Getting Started

### Prerequisites

- Java 9
- Maven 3.6+
- Chrome and firefox installed (for local execution)
- Git

## 🧪  How to run the tests

To run the tests in firefox

```bash
mvn clean test -Dbrowser=firefox
```

To run the tests in firefox

```bash
mvn clean test -Dbrowser=chrome
```

To run the tests for a specific scenario, below are the available tags:
- @successfulSubmit
- @validateErrorMessages

```bash
mvn clean test -Dcucumber.filter.tags="@successfulSubmit" -Dbrowser=firefox  
```

## Reports
The reports are generated under the name `target/cucumber-reports.html`. The reports are in HTML format and can be opened in any browser.

## Recommended settings
For some unknown reason, the tests are running very slowly in chrome browser, hence it is recommended to run the tests in firefox browser.

## To do
Due to time constraints, the following features are not yet implemented, going forward I'll add them
- Parallel execution
- Allure/Extent report integration
- Logging framework support
- Executing the tests via github actions against every pull request


