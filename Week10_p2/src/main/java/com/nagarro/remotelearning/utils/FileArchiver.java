package com.nagarro.remotelearning.utils;

import com.nagarro.remotelearning.main.Main;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.zip.GZIPOutputStream;

public class FileArchiver {

    public void archive(String source, String destination) throws IOException, URISyntaxException {
        BufferedReader reader = new BufferedReader(
                new FileReader(getPathFromResource(source)));
        BufferedOutputStream writter = new BufferedOutputStream(
                new GZIPOutputStream(new FileOutputStream(getPathFromResource(destination))));
        int element;
        while ((element = reader.read()) != -1) {
            writter.write(element);
        }
        reader.close();
        writter.close();
        //treat exceptions
        //close all output.input streams
    }

    private String getPathFromResource(String fileName) throws URISyntaxException {
        URL res = Main.class.getClassLoader().getResource(fileName);
        //NPE
        File file = Paths.get(res.toURI()).toFile();
        return file.getAbsolutePath();
    }
}
