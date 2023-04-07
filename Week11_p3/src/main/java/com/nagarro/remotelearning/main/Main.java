package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.utils.DatabaseInformer;

public class Main {
    public static void main(String[] args) {
        DatabaseInformer databaseInformer = new DatabaseInformer();
        System.out.println(databaseInformer.getDatabaseColumns());
    }
}