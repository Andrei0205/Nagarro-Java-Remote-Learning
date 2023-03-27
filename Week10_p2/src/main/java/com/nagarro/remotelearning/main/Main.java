package com.nagarro.remotelearning.main;


import com.nagarro.remotelearning.utils.FileArchiver;
import com.nagarro.remotelearning.utils.FileInformer;

import java.io.*;
import java.net.URISyntaxException;


public class Main {
    public static void main(String[] args)
            throws IOException, URISyntaxException {

        FileArchiver fileArchiver = new FileArchiver();
        fileArchiver.readAndArchive("uncompressed.txt", "compressedFile.gz");
        FileInformer fileInformer = new FileInformer();
        double compressedFileSize = fileInformer.getFileSize("compressedFile.gz");
        double uncompressedFileSize = fileInformer.getFileSize("uncompressed.txt");
        double compressionRatio = compressedFileSize / uncompressedFileSize;
        System.out.println("Compression ration: " + compressionRatio);

    }

}
