package com.nagarro.remotelearning.utils;

import com.nagarro.remotelearning.main.Main;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public abstract class FileInteract {
    protected String getPathFromResource(String fileName) {
        URL res = Main.class.getClassLoader().getResource(fileName);
        if (res != null) {
            try {
                File file = Paths.get(res.toURI()).toFile();
                return file.getAbsolutePath();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
