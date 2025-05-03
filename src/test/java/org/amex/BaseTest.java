package org.amex;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.amex.selenium.Driver;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseTest {

    @Autowired
    protected Driver driver;

    @Before
    public void initiateDriver(){
        driver.createNewSession();
        driver.getDriver().manage().window().maximize();
    }

    @After
    public void tearDown(){
        driver.endBrowserSession();
    }
}
