package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.utils.DirectoryManager;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String path = "src/main";
        DirectoryManager directoryManager = new DirectoryManager(path);
        System.out.println(Arrays.toString(directoryManager.getSubdirectories()));
        System.out.println(Arrays.toString(directoryManager.getSubdirectoriesWithLambda()));
        System.out.println(Arrays.toString(directoryManager.getSubdirectoriesWithMethodReference()));
    }
}