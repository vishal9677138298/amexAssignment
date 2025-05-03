package org.amex.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.amex.pages.AllCardsPage;
import org.amex.pages.GoldExpressPage;
import org.amex.pages.HomePage;
import org.amex.pages.RequestNewCardPage;
import org.amex.pages.components.CookieConsentPopUp;
import org.amex.selenium.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;


public class GoldCardApplicationSteps {

    private Driver driver;

    @Autowired
    private HomePage homePage;

    @Autowired
    private AllCardsPage allCardsPage;

    @Autowired
    private RequestNewCardPage requestNewCardPage;

    @Autowired
    private GoldExpressPage goldExpressPage;

    @Autowired
    public GoldCardApplicationSteps(Driver driver){
        this.driver = driver;
    }

    @Given("the user is on the American Express homepage")
    public void navigateToHomePage(){
        homePage.navigate();
    }

    @And("{string} all cookies")
    public void handleCookieConsent(String cookieOption){
        switch (cookieOption.toLowerCase()){
            case "accepts":
                homePage.handleCookiePopup(CookieConsentPopUp.CookieOptions.ACCEPT_ALL);
                break;
            case "rejects":
                homePage.handleCookiePopup(CookieConsentPopUp.CookieOptions.REJECT_ALL);
                break;
            default:
                throw new IllegalArgumentException("Invalid cookie option: " + cookieOption + ". Please use 'accepts' or 'rejects'.");
        }
    }

    @And("the user views all the available cards under {string}")
    public void theUserViewsTheAvailableCardsUnderPersonalCards(String cardType) {
        HomePage.Cards card = HomePage.Cards.getValue(cardType);
        homePage.viewCards(card);
    }

    @And("the user navigates to the {string} application page")
    public void theUserClickOnLeanMoreButtonFor(String cardType) {
        AllCardsPage.AllCards card = AllCardsPage.AllCards.getValue(cardType);
        allCardsPage.checkAndAcceptCookieConsent().learnMore(card);
    }

    @And("requests a new card")
    public void theUserRequestForANewCard() {
        goldExpressPage.requestNewCard();
    }

    @When("the user fills in the application form with the following valid data, then the form must be successfully submitted:")
    @Then("the user fills in the application form with the following junk data, then the validation error messages must be displayed:")
    public void theUserEnterTheDataInTheBelowFieldsAndChecksTheValidationErrors(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        requestNewCardPage.checkAndAcceptCookieConsent();

        /**
         * Iterates over each row of data, fills the application form, and validates the result.
         *
         * For each row:
         * - If the "messagesToCheck" field is null, it validates that the form was successfully submitted.
         * - If the "messagesToCheck" field is not null, it validates the error messages displayed on the form.
         *
         * @param data A list of maps where each map represents a row of form data.
         *             Each map contains key-value pairs for form fields and validation messages.
         *             Example keys: "civility", "firstName", "lastName", "emailAddress", "phoneNumber", "dateOfBirth", "messagesToCheck".
         */
        data.forEach(row -> {
            // Fill the application form with the data from the current row
            requestNewCardPage.fillFormData(row);

            // Check if "messagesToCheck" is null
            if (row.get("messagesToCheck") == null) {
                // Validate that the form was successfully submitted
                requestNewCardPage.validateSuccessfulSubmit();
            } else {
                // Validate the error messages displayed on the form
                requestNewCardPage.validateErrorMessage(
                        row.get("messagesToCheck") // Retrieve the "messagesToCheck" field
                                .replaceAll("\\s+", "") // Remove all whitespace
                                .split(",") // Split the string into an array of messages
                );
            }
        });

    }
}
