package com.nagarro.remotelearning.utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class UTFWritter {
    public void write(String file, String text) throws IOException, URISyntaxException {
        RandomAccessFile writter = new RandomAccessFile(getPathFromResource(file), "rw");
        writter.writeUTF(text);
        writter.close();
    }

    private String getPathFromResource(String fileName) throws URISyntaxException {
        URL res = this.getClass().getClassLoader().getResource(fileName);
        File file = Paths.get(res.toURI()).toFile();
        return file.getAbsolutePath();
    }
}
