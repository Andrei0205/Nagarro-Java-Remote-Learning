package com.nagarro.remotelearning.utils;

public class Consumer implements Runnable {
    private Server server;
    private int id;

    public Consumer(Server server,int id) {
        this.server = server;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            server.consume(id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
