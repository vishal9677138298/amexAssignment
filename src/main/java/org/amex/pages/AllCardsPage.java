package org.amex.pages;

import io.cucumber.spring.ScenarioScope;
import org.amex.pages.components.CookieConsentPopUp;
import org.amex.selenium.Driver;
import org.amex.selenium.ExplicitWaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
@ScenarioScope
public class AllCardsPage extends Page<AllCardsPage> {
    private ExplicitWaitHelper expWait;
    private String cardTypeXpath = "//*[contains(@class, 'product-nav-item')]//p[contains(text(), '%s')]";

    private String learnMoreButtonXpath = "//a[contains(@href, '%s') and contains(text(), 'En savoir plus')]";

    @Autowired
    private CookieConsentPopUp cookieConsentPopUp;

    public enum AllCards {
        PLATINUM_EXPRESS("CartePlatinumAmericanExpress", "Carte Platinum American Express"),
        GOLD_EXPRESS("GoldCardAmericanExpress", "Cartes Gold American Express"),
        AIR_FRANCE("AirFranceKLM", "Carte AIR FRANCE KLM - AMERICAN EXPRESS GOLD");

        private String cardLinkText, visibleText;

        public String getCardLinkText() {
            return cardLinkText;
        }

        AllCards(String cardLinkText, String visibleText) {
            this.cardLinkText = cardLinkText;
            this.visibleText = visibleText;
        }

        public static AllCards getValue(String value){
            for(AllCards card: AllCards.values()){
                if(card.visibleText.equalsIgnoreCase(value)){
                    return card;
                }
            }
            throw new IllegalArgumentException("No matching card type found for value: " + value);
        }
    }

    @Autowired
    public AllCardsPage(Driver driver) {
        super(driver);
        this.expWait = new ExplicitWaitHelper(driver, 10);
    }

    public void learnMore(AllCards card){
        WebElement cardElement = driver.getDriver()
                .findElement(By.xpath(String.format(learnMoreButtonXpath, card.getCardLinkText())));
        expWait.waitTillVisibility(cardElement);
        cardElement.click();
    }

}
