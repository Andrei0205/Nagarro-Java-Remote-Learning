package com.nagarro.remotelearning.utils;

import java.io.*;
import java.nio.charset.Charset;

public class Reader extends FileInteract {
    public String read(String file, Charset charset) {

        try (FileInputStream fileInputStream = new FileInputStream(getPathFromResource(file));
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, charset);
             BufferedReader reader = new BufferedReader(inputStreamReader);) {
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("File not found");
        }
        return "";
    }

}
