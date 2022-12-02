package com.nagarro.remotelearning.main;


import com.nagarro.remotelearning.app.ConnectionsManager;

public class Main {
   static ConnectionsManager connectionsManager = new ConnectionsManager();

    public static void main(String[] args) {
        System.out.println(connectionsManager.getConnection(2));
        System.out.println(connectionsManager.getConnection(21));

    }
}