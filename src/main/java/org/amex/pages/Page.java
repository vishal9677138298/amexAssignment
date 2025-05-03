package org.amex.pages;

import org.amex.pages.components.CookieConsentPopUp;
import org.amex.selenium.Driver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Page<T extends Page> {
    protected Driver driver;

    @Autowired
    private CookieConsentPopUp cookieConsentPopUp;

    public Page(Driver driver){
        this.driver = driver;
        PageFactory.initElements(driver.getDriver(), this);
    }

    public T checkAndAcceptCookieConsent(){
        if(cookieConsentPopUp.isLoaded()){
            cookieConsentPopUp.handleCookies(CookieConsentPopUp.CookieOptions.ACCEPT_ALL);
        }
        return (T) this;
    }

}
