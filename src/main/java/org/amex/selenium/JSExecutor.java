package org.amex.selenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JSExecutor {

    private WebDriver driver;

    public JSExecutor(WebDriver driver){
        this.driver = driver;
    }

    public void scrollIntoView(WebElement element){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    public boolean waitTillPageIsLoaded(){
        return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
    }

    public void clickElement(WebElement element){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}
