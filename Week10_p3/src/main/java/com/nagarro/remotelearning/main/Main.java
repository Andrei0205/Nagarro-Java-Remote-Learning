package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.utils.*;
import com.nagarro.remotelearning.utils.Reader;
import java.nio.charset.StandardCharsets;


public class Main {
    public static void main(String[] args) {
        Reader reader = new Reader();
        MyWritter writter = new MyWritter();
        String textLatinToUTF = reader.read("latinText.txt", StandardCharsets.ISO_8859_1);
        writter.write("test.txt", textLatinToUTF, StandardCharsets.UTF_8);

        String textUTFToLatin = reader.read("UTFText.txt", StandardCharsets.UTF_8);
        writter.write("latinFromUTF.txt", textUTFToLatin, StandardCharsets.ISO_8859_1);

    }

}
