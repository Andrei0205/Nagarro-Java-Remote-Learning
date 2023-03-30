package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.utils.RandomAccessReader;
import com.nagarro.remotelearning.utils.RandomAccessWritter;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        URL res = Main.class.getClassLoader().getResource("file.txt");
        File file = Paths.get(res.toURI()).toFile();
        String filePath = file.getAbsolutePath();
        RandomAccessWritter writter = new RandomAccessWritter(filePath);
        RandomAccessReader reader = new RandomAccessReader(filePath);
        writter.writeSomeInteger();
        reader.readAtPosition(2);
        reader.readAtPosition(3);
    }
}