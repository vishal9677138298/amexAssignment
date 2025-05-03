package org.amex.utils;

public class LanguageHandler {

    public enum Languages {
        FRENCH("french"),
        ENGLISH("english");
        private final String lang;


        Languages(String lang) {
            this.lang = lang;
        }

        public String getLang() {
            return lang;
        }

    }

    /**
     * Retrieves the runtime language based on the system property "LANGUAGE".
     * Defaults to "french" if the property is not set.
     *
     * @return The corresponding Languages enum value.
     * @throws RuntimeException if an unidentified language is supplied.
     */
    public static Languages getRuntimeLanguage() {
        String language = System.getProperty("LANGUAGE", "french").toLowerCase();
        switch (language) {
            case "english":
                return Languages.ENGLISH;
            case "french":
                return Languages.FRENCH;
            default:
                throw new RuntimeException("Unidentified language supplied");
        }
    }
}
