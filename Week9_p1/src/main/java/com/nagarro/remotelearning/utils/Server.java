package com.nagarro.remotelearning.utils;

import java.util.LinkedList;
import java.util.List;

public class Server {

    private final List<Integer> log = new LinkedList<>();
    private static int value = 0;

    public synchronized void add(int id) throws InterruptedException {

        while (true) {
            int capacity = 10;
            while (log.size() == capacity) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Thread " + id + " added task => " + value);
            log.add(value++);
            notifyAll();
        }
    }


    public synchronized void consume(int id) throws InterruptedException {
        int val;
        while (true) {
            while (log.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            val = log.remove(0);
            System.out.println("Thread " + id + " consumed task => " + val);
            notifyAll();
            Thread.sleep(200);
        }
    }
}
