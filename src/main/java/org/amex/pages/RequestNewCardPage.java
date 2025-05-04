package org.amex.pages;

import io.cucumber.spring.ScenarioScope;
import org.amex.selenium.Driver;
import org.amex.selenium.ExplicitWaitHelper;
import org.amex.selenium.JSExecutor;
import org.amex.utils.JsonUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

@Component
@Lazy
@ScenarioScope
public class RequestNewCardPage extends Page {
    private ExplicitWaitHelper expWait;

    @FindBy(xpath = "//label[@for='MR']")
    private WebElement mrRadioButton;

    @FindBy(xpath = "//label[@for='MS']")
    private WebElement msRadioButton;

    @FindBy(xpath = "//input[@id='fieldControl-input-firstName']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@id='fieldControl-input-lastName']")
    private WebElement lastNameInput;

    @FindBy(xpath = "//input[@id='fieldControl-input-dateOfBirth']")
    private WebElement dateOfBirthInput;

    @FindBy(xpath = "//input[@id='fieldControl-input-email']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@id='fieldControl-input-mobilePhoneNumber']")
    private WebElement mobilePhoneInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//input")
    private List<WebElement> allInputElements;

    private String personalInformationHeader = "//h2[text()='%s']";
           // "//h2[text()='Vos informations personnelles']"

    @Autowired
    public RequestNewCardPage(Driver driver) {
        super(driver);
        this.expWait = new ExplicitWaitHelper(driver, 15);
    }

    public void fillFormData(Map<String, String> formData) {
        expWait.waitTillPageIsLoaded();
        JSExecutor jsExecutor = new JSExecutor(driver.getDriver());
        String firstName = Optional.ofNullable(formData.get("firstName")).orElse("");
        String lastName = Optional.ofNullable(formData.get("lastName")).orElse("");
        String dateOfBirth = Optional.ofNullable(formData.get("dateOfBirth")).orElse("");
        String email = Optional.ofNullable(formData.get("emailAddress")).orElse("");
        String mobilePhone = Optional.ofNullable(formData.get("phoneNumber")).orElse("");
        String civility = Optional.ofNullable(formData.get("civility")).orElse("");
        BiConsumer<WebElement, String> fillData = (element, dataToFill) -> {
            if (!dataToFill.isEmpty()){
                element.sendKeys(dataToFill);
            }
        };

        expWait.waitTillVisibility(firstNameInput,
                lastNameInput, dateOfBirthInput, emailInput, mobilePhoneInput, submitButton);

        if (civility.equalsIgnoreCase("mr")) {
            jsExecutor.clickElement(mrRadioButton);
        } else if (civility.equalsIgnoreCase("ms")) {
            jsExecutor.clickElement(msRadioButton);
        }

        fillData.accept(firstNameInput, firstName);
        fillData.accept(lastNameInput, lastName);
        fillData.accept(dateOfBirthInput, dateOfBirth);
        fillData.accept(emailInput, email);
        fillData.accept(mobilePhoneInput, mobilePhone);

        submit();
    }

    public void validateErrorMessage(String[] errorMessages) {
        expWait = new ExplicitWaitHelper(driver, 30);
        Arrays.stream(errorMessages)
                .map(errorMessage ->
                        JsonUtils.readJsonPropertiesBasedOnLang("homePage.json", "errorMessages." + errorMessage))
                .forEach(errorMessage -> {
                    By errorLocator = By.xpath(String.format("//*[contains(text(), '%s')]", errorMessage));
                    expWait.waitTillVisibility(errorLocator);
                    Assert.assertTrue(String.format("Error message not displayed: %s", errorMessage),
                            driver.getDriver().findElement(errorLocator).isDisplayed());
                });
    }

    public void submit(){
        JSExecutor jsExecutor = new JSExecutor(driver.getDriver());
        jsExecutor.scrollIntoView(submitButton);
        expWait.get().until(ExpectedConditions.elementToBeClickable(submitButton));
        jsExecutor.clickElement(submitButton);
    }

    public void validateSuccessfulSubmit(){
        expWait.waitTillPageIsLoaded();
        expWait = new ExplicitWaitHelper(driver, 3);
        expWait.waitTillVisibility(By.xpath(String.format(personalInformationHeader,
                    JsonUtils.readJsonPropertiesBasedOnLang("homePage.json", "personalInformationHeader"))));
    }
}
