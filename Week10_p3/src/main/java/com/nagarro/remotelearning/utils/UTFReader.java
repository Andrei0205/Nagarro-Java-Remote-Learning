package com.nagarro.remotelearning.utils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class UTFReader {
    public String read(String file) throws IOException, URISyntaxException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(getPathFromResource(file)), StandardCharsets.UTF_8));
        return reader.readLine();
        //close streams
        //la tratarea exceptiilor de inchis stream
    }

    private String getPathFromResource(String fileName) throws URISyntaxException {
        URL res = this.getClass().getClassLoader().getResource(fileName);
        File file = Paths.get(res.toURI()).toFile();
        return file.getAbsolutePath();
    }

    //abstract class + NPE
}