package com.nagarro.remotelearning.utils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class LatinWritter {
    public void write(String file, String text) throws URISyntaxException, IOException {
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getPathFromResource(file)), StandardCharsets.ISO_8859_1));
        writer.write(text);
        writer.close();
        //stream close + NPE
    }

    private String getPathFromResource(String fileName) throws URISyntaxException {
        URL res = this.getClass().getClassLoader().getResource(fileName);
        File file = Paths.get(res.toURI()).toFile();
        return file.getAbsolutePath();
    }
}
