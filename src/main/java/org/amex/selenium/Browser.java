package org.amex.selenium;

public enum Browser {
    CHROME, FIREFOX, SAFARI;

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
    public static Browser getRuntimeBrowser() {
        String requestedBrowser = System.getProperty("browser");
        if (requestedBrowser == null) {
            return CHROME;
        }

        switch (requestedBrowser) {
            case "chrome":
                return CHROME;
            case "firefox":
                return FIREFOX;
            case "safari":
                return SAFARI;
            default:
                return CHROME; // Default fallback
        }
    }
}