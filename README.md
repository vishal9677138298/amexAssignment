# Amex interview assessment

This project is a test automation framework using **Selenium WebDriver**, **Cucumber (BDD)**, and **Maven**, built in **Java**.

---

## 🛠️ Technologies Used

- Java
- Maven
- Selenium WebDriver
- Cucumber (Gherkin)
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

## Recommended settings
For some unknown reason, the tests are running very slowly in chrome browser, hence it is recommended to run the tests in firefox browser.


