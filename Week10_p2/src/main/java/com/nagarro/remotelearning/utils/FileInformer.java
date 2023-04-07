package com.nagarro.remotelearning.utils;

import com.nagarro.remotelearning.main.Main;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileInformer {
    public double getFileSize(String fileName) {
        double bytes = 0;

        if (getUrlFromResourceFile(fileName) != null) {
            Path path = Paths.get(getUrlFromResourceFile(fileName));
            try {
                bytes = Files.size(path);
            } catch (IOException e) {
                System.out.println("File not found");
            }
        }
        return bytes;
    }

    private URI getUrlFromResourceFile(String fileName) {
        try {
            return Main.class.getClassLoader().getResource(fileName).toURI();
        } catch (URISyntaxException | NullPointerException e) {
            System.out.println("File not found");
        }
        return null;
    }
}
