package com.nagarro.remotelearning.utils;

import java.util.LinkedList;

public class Server {

    private LinkedList<Integer> list = new LinkedList<>();
    private static int value = 0;

    public synchronized void add(int id) throws InterruptedException {

        while (true) {
            int capacity = 10;
            while (list.size() == capacity) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Thread " + id + " added task => " + value);
            list.add(value++);
            notifyAll();
            Thread.sleep(200);
        }
    }


    public synchronized void consume(int id) throws InterruptedException {
        int val;
        while (true) {
            while (list.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            val = list.removeFirst();
            System.out.println("Thread " + id + " consumed task => " + val);
            notifyAll();
            Thread.sleep(200);
        }
    }
}
