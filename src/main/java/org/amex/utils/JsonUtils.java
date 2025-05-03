package org.amex.utils;

import com.jayway.jsonpath.JsonPath;

import java.io.IOException;

public class JsonUtils {


    /**
     * Reads a JSON file and retrieves a value based on the specified language and path.
     *
     * @param fileName The name of the JSON file (without the .json extension).
     * @param path     The JSON path to the value to be retrieved.
     * @return The value retrieved from the JSON file.
     */
    public static String readJsonPropertiesBasedOnLang(String fileName, String path){
        String runTimeLang = LanguageHandler.getRuntimeLanguage().getLang();
        FileReader fileReader = new FileReader("language-bundles/"+fileName);
        try {
            return JsonPath.read(fileReader.readFileAsStream(), String.format("$.%s.%s", runTimeLang, path));
        } catch (IOException e) {
            throw new RuntimeException("Error reading JSON file: " + fileName + " with path: " + path, e);
        }

    }
}
