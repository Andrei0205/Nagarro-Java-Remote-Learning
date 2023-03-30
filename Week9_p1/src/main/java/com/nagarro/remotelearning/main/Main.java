package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.utils.Client;
import com.nagarro.remotelearning.utils.Consumer;
import com.nagarro.remotelearning.utils.Server;

public class Main {
    public static void main(String[] args) {
        final Server server = new Server();

        Thread t1 = new Thread(new Client(server,1));
        Thread t2 = new Thread(new Client(server,2));

        Thread t3 = new Thread(new Consumer(server,3));


        t1.start();
        t2.start();

        t3.start();
    }
}