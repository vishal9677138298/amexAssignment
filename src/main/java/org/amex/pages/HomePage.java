package org.amex.pages;

import io.cucumber.spring.ScenarioScope;
import org.amex.pages.components.CookieConsentPopUp;
import org.amex.selenium.Driver;
import org.amex.selenium.ExplicitWaitHelper;
import org.amex.utils.JsonUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
@ScenarioScope
public class HomePage extends Page<HomePage> {
    private ExplicitWaitHelper expWait;
    public enum Cards {
        PERSONAL_CARDS("personal cards"),
        PREMIUM_CARDS("premium cards"),
        TPSSME_CARDS("tps/sme cards"),

        CORPORATE_CARDS("corporate cards");

        private String cardType;

        Cards(String cardType) {
            this.cardType = cardType;
        }

        public String getLangBasedRepresentation() {
            switch (this) {
                case PERSONAL_CARDS:
                    return JsonUtils.readJsonPropertiesBasedOnLang("homePage.json", "products.personalCards");
                case PREMIUM_CARDS:
                    return JsonUtils.readJsonPropertiesBasedOnLang("homePage.json", "products.premiumCards");
                case TPSSME_CARDS:
                    return JsonUtils.readJsonPropertiesBasedOnLang("homePage.json", "products.tpsMseCards");
                case CORPORATE_CARDS:
                    return JsonUtils.readJsonPropertiesBasedOnLang("homePage.json", "products.corporateCards");

                default:
                    throw new IllegalArgumentException("Unknown card type: " + this);
            }
        }

        public static Cards getValue(String value){
            for(Cards card: Cards.values()){
                if(card.cardType.equalsIgnoreCase(value)){
                    return card;
                }
            }
            throw new IllegalArgumentException("No matching card type found for value: " + value);
        }
    }

    @Autowired
    private CookieConsentPopUp cookieConsentPopUp;

    private String cardTypeXpath = "//*[contains(@class, 'product-nav-item')]//p[contains(text(), '%s')]";

    @Autowired
    public HomePage(Driver driver) {
        super(driver);
        this.expWait = new ExplicitWaitHelper(driver, 10);
    }


    /**
     * This method is used to navigate to the home page of American Express.
     * It reads the URL from a JSON file and uses the WebDriver to open it.
     */
    public void navigate() {
        String url = JsonUtils.readJsonPropertiesBasedOnLang("homePage.json", "url");
        driver.getDriver().get(url);
        expWait.waitTillPageIsLoaded();
    }

    /**
     * This method handles the cookie consent pop-up on the American Express home page.
     * It accepts or rejects cookies based on the provided option.
     *
     * @param option The option to handle cookies (ACCEPT_ALL, REJECT_ALL, MANAGE).
     */
    public void handleCookiePopup(CookieConsentPopUp.CookieOptions option) {
        cookieConsentPopUp.get().handleCookies(option);
    }

    public void viewCards(Cards cardType) {
        String xpath = String.format(cardTypeXpath, cardType.getLangBasedRepresentation());
        WebElement cardTypeElement = driver.getDriver().findElement(By.xpath(xpath));
        expWait.waitTillVisibility(cardTypeElement);
        cardTypeElement.click();
    }


}
