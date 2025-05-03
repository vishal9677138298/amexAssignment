Feature: American Express Gold Card Application
  As a potential American Express customer
  I want to navigate through the AMEX website and apply for a Gold card
  So that I can complete the application process

  Background:
    Given the user is on the American Express homepage
    And "accepts" all cookies
    And the user views all the available cards under "personal cards"
    When the user navigates to the "Cartes Gold American Express" application page
    And requests a new card

  @validateErrorMessages
  Scenario: Validate error messages during Gold Card application
    When the user fills in the application form with the following junk data, then the validation error messages must be displayed:
      | civility | firstName | lastName | emailAddress     | phoneNumber | dateOfBirth | messagesToCheck                                                   |
      |          |           |          |                  |             |             | civility, firstName, lastName, dateOfBirth, email, phone, generic |
      | *&^*&^   | *&%$      | *&^*     | #$%^$%           | 1234        | *&^*&       | civility, firstName, lastName, dateOfBirth, email, phone, generic |
      | mr       | vishal    | sugumar  | vishal@gmail.com | 123         | 23041996    | phone, generic                                                    |

  @successfulSubmit
  Scenario: Validate successful Gold Card application
    When the user fills in the application form with the following valid data, then the form must be successfully submitted:
      | civility | firstName | lastName | emailAddress     | phoneNumber | dateOfBirth | messagesToCheck |
      | mr       | vishal    | sugumar  | vishal@gmail.com | 0612345678  | 23041996    |                 |
