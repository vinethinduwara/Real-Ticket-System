package org.example.secondlastcopy.util;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JasonFileUtility {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> void saveToFile(String filePath, T data) {
        try {
            objectMapper.writeValue(new File(filePath), data);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save configuration: " + e.getMessage());
        }
    }

    public static <T> T loadFromFile(String filePath, Class<T> clazz) {
        try {
            return objectMapper.readValue(new File(filePath), clazz);
        } catch (IOException e) {
            System.err.println("Failed to load configuration: " + e.getMessage());
            return null;
        }
    }
}
