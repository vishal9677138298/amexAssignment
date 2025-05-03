package org.amex.pages.components;

import io.cucumber.spring.ScenarioScope;
import org.amex.pages.Page;
import org.amex.selenium.Driver;
import org.amex.selenium.ExplicitWaitHelper;
import org.amex.utils.JsonUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


import java.util.function.Consumer;

@Component
@Lazy
@ScenarioScope
public class CookieConsentPopUp extends Page implements ILoadedComponent<CookieConsentPopUp> {
    private ExplicitWaitHelper expWait;
    @FindBy(className = "granular-banner")
    private WebElement cookiePopup;

    private String cookieHeadingXpath = String.format("//h1[text()='%s']",
            JsonUtils.readJsonPropertiesBasedOnLang("homePage.json", "cookieHeader"));

    @FindBy(css = "[data-testid='granular-banner-button-accept-all']")
    private WebElement acceptAllButton;

    @FindBy(css = "[data-testid='granular-banner-button-reject-all']")
    private WebElement rejectAllButton;

    public enum CookieOptions {
        ACCEPT_ALL,
        REJECT_ALL,
        MANAGE
    }

    public CookieConsentPopUp(Driver driver) {
        super(driver);
        this.expWait = new ExplicitWaitHelper(driver, 5);
    }

    @Override
    public boolean isLoaded() {
        this.expWait = new ExplicitWaitHelper(driver, 5);
        try {
            expWait.waitTillVisibility(By.xpath(cookieHeadingXpath));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Handles cookie consent actions based on the specified option.
     *
     * This method waits for the visibility of the corresponding button and clicks it
     * based on the provided cookie option (ACCEPT_ALL or REJECT_ALL).
     *
     * @param option The cookie option to handle, specified as an enum of `CookieOptions`.
     */
    public void handleCookies(CookieOptions option){
        Consumer<WebElement> handle = (button) -> {
            expWait.waitTillVisibility(button);
            button.click();
        };

        switch (option){
            case ACCEPT_ALL:
                handle.accept(acceptAllButton);
                break;
            case REJECT_ALL:
                handle.accept(rejectAllButton);
                break;
        }

    }


}
