package org.amex.utils;

import java.io.InputStream;
import java.util.Optional;

public class FileReader {

    private String filePath;
    private InputStream is;

    public FileReader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads a file as an InputStream.
     *
     * @return InputStream of the file content.
     * @throws RuntimeException if the file specified by `filePath` is not found.
     */
    public InputStream readFileAsStream() {
        if (is != null) {
            return is;
        }
        return Optional.ofNullable(JsonUtils.class.getClassLoader().getResourceAsStream(filePath))
                .orElseThrow(() -> new RuntimeException(filePath + " file not found"));
    }

}
