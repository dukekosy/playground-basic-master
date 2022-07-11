package com.smile.io;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

/**
 * FileAccess class used for file io operations
 *
 * @author Kosala Amarasinghe
 * @version 1.0
 */
public class FileAccess {

    /*
     * This is a static method to load the given file from the classpath
     * @param fileName the name of the file
     * @return File FileAccess instance
     */
    private File getFileFromResource(String fileName) throws URISyntaxException {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return new File(resource.toURI());
        }

    }

    /*
     * This is a static method to load the given file from the classpath and read all lines
     *
     * @param fileName the name of the file
     * @return list of family names from the file
     */
    public List<String> getNamesFromFile(String fileName) {
        try {
            return Files.readAllLines(getFileFromResource(fileName).toPath(), StandardCharsets.UTF_8);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
