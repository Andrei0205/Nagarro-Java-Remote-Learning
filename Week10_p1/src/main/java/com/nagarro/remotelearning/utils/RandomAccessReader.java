package com.nagarro.remotelearning.utils;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessReader {
    private String file;
    public RandomAccessReader(String file) {
        this.file = file;
    }

    public void readAtPosition(int position) throws IOException {
        RandomAccessFile reader = new RandomAccessFile(file,"r");
        reader.seek(position);
        System.out.println(reader.read());
        reader.close();
    }
}
