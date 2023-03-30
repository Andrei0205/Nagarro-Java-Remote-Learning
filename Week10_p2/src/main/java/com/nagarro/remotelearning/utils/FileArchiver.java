package com.nagarro.remotelearning.utils;

import com.nagarro.remotelearning.main.Main;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.zip.GZIPOutputStream;

public class FileArchiver {

    public void archive(String source, String destination) {
        try (
                FileReader fileReader = new FileReader(getPathFromResource(source));
                BufferedReader reader = new BufferedReader(fileReader);
                FileOutputStream fileOutputStream = new FileOutputStream(getPathFromResource(destination));
                GZIPOutputStream gzipOutputStream = new GZIPOutputStream(fileOutputStream);
                BufferedOutputStream writter = new BufferedOutputStream(gzipOutputStream);
                ) {
            int element;
            while ((element = reader.read()) != -1) {
                writter.write(element);
            }
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    private String getPathFromResource(String fileName) {
        URL res = Main.class.getClassLoader().getResource(fileName);
        if(res != null) {
            try {
                File file = Paths.get(res.toURI()).toFile();
                return file.getAbsolutePath();
            } catch (URISyntaxException e) {
                System.out.println("File not found");
            }
        }
        return "not found";
    }
}
