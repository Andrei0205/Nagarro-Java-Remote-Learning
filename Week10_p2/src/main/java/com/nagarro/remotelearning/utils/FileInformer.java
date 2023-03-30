package com.nagarro.remotelearning.utils;

import com.nagarro.remotelearning.main.Main;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileInformer {
    public double getFileSize(String fileName) throws URISyntaxException {
        Path path = Paths.get(getUrlFromResourceFile(fileName));
        double bytes = 0;
        try {
            bytes = Files.size(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    private URI getUrlFromResourceFile(String fileName) throws URISyntaxException {
        return Main.class.getClassLoader().getResource(fileName).toURI();
        //todo NPE
    }
}
