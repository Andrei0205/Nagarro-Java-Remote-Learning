package com.nagarro.remotelearning.utils;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessWritter {
    private String file;
    public RandomAccessWritter(String file) {
        this.file = file;
    }

    public void writeSomeInteger() throws IOException {
        RandomAccessFile writter = new RandomAccessFile(file, "rw");
        for (int i = 0; i < 8; i += 2) {
            writter.write(i);
        }
        writter.close();
    }
}
