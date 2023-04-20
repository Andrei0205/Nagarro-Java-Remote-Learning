package com.nagarro.remotelearning.utils;

import java.io.File;
import java.io.FileFilter;

public class DirectoryManager {
    private final String path;

    public DirectoryManager(String path) {
        this.path = path;
    }

    public File[] getSubdirectories() {
        File file = getFileFromPath(path);
        return file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return new File(pathname.toURI()).isDirectory();
            }
        });
    }

    public File[] getSubdirectoriesWithLambda() {
        File file = getFileFromPath(path);
        return file.listFiles(dir -> dir.isDirectory());
    }

    public File[] getSubdirectoriesWithMethodReference() {
        File file = getFileFromPath(path);
        return file.listFiles(File::isDirectory);
    }

    private File getFileFromPath(String path) {
        try {
            return new File(path);
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
    }
}