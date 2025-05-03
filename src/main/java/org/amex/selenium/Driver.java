package org.amex.selenium;

import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
public class Driver {

    private ThreadLocal<WebDriver> threadSafeDriver = new ThreadLocal<>();

    /**
     * Determines the runtime browser based on the system property "browser".
     *
     * If the "browser" system property is not set, it defaults to CHROME.
     * Recognized values for the "browser" property are:
     * - "chrome" for the CHROME browser.
     * - "firefox" for the FIREFOX browser.
     * - "safari" for the SAFARI browser.
     *
     * Any unrecognized value will also default to CHROME.
     *
     * @return The Browser enum value corresponding to the runtime browser.
     */
    public void createNewSession(){
        Browser browser = Browser.getRuntimeBrowser();

        switch (browser) {
            case FIREFOX:
                threadSafeDriver.set(new FirefoxDriver());
                break;
            case SAFARI:
                threadSafeDriver.set(new SafariDriver());
                break;
            default:
                threadSafeDriver.set(new ChromeDriver());
                break;
        }
    }

    public WebDriver getDriver(){
        if(threadSafeDriver.get() == null){
            throw new RuntimeException("Web driver not initiated");
        }
        return this.threadSafeDriver.get();
    }

    public void endBrowserSession(){
        threadSafeDriver.get().quit();
    }
}
