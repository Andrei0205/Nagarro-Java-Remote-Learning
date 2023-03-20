package com.nagarro.remotelearning.utils;

public class Client implements Runnable{
    private Server server;
    private int id;

    public Client(Server server,int id) {
        this.server = server;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            server.add(id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
