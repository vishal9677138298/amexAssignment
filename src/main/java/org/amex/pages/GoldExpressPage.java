package org.amex.pages;

import io.cucumber.spring.ScenarioScope;
import org.amex.selenium.Driver;
import org.amex.selenium.ExplicitWaitHelper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
@ScenarioScope
public class GoldExpressPage extends Page<GoldExpressPage> {
    private ExplicitWaitHelper expWait;

    @FindBy(xpath = "//*[@data-qe-id='Desktop']//following-sibling::div//*[contains(text(), 'Demandez votre Carte')]")
    private WebElement requestNewCardButton;

    @Autowired
    public GoldExpressPage(Driver driver) {
        super(driver);
        this.expWait = new ExplicitWaitHelper(driver, 10);
    }

    public void requestNewCard() {
        expWait.waitTillVisibility(requestNewCardButton);
        requestNewCardButton.click();
    }


}
