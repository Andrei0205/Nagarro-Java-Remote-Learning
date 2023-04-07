package com.nagarro.remotelearning.utils;

import java.io.*;
import java.nio.charset.Charset;

public class MyWritter extends FileInteract {
    public void write(String file, String text, Charset charset) {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(getPathFromResource(file));
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, charset);
                Writer writer = new BufferedWriter(outputStreamWriter);
        ) {
            writer.write(text);
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

}
