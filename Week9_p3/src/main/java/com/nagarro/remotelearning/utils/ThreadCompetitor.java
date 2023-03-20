package com.nagarro.remotelearning.utils;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class ThreadCompetitor implements Runnable {

    private final CountDownLatch latch;
    private static Random rand = new Random();

    public ThreadCompetitor( CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            doWork();
            latch.countDown();
        } catch (InterruptedException e) {
            System.out.println(this + "interrupted");
        }
    }

    private void doWork() throws InterruptedException {
        Thread.sleep(rand.nextInt(200));
    }
}
