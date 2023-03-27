package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.utils.LatinReader;
import com.nagarro.remotelearning.utils.LatinWritter;
import com.nagarro.remotelearning.utils.UTFReader;
import com.nagarro.remotelearning.utils.UTFWritter;

import java.io.*;
import java.net.URISyntaxException;


public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        LatinReader latinReader = new LatinReader();
        UTFWritter utfWritter = new UTFWritter();
        String textLatinToUTF = latinReader.read("latinText.txt");
        utfWritter.write("test.txt", textLatinToUTF);

        UTFReader utfReader = new UTFReader();
        LatinWritter latinWritter = new LatinWritter();
        String textUTFToLatin = utfReader.read("UTFText.txt");
        latinWritter.write("latinFromUTF.txt", textUTFToLatin);

    }

}
