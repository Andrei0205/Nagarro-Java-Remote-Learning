package com.nagarro.remotelearning.utils;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class ThreadCompetitor implements Runnable {
    private final CountDownLatch latch;
    private final Semaphore semaphore;
    private static final Random rand = new Random();

    public ThreadCompetitor(Semaphore semaphore, CountDownLatch latch) {
        this.latch = latch;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            doWork();
            latch.countDown();
            semaphore.release();
        } catch (InterruptedException e) {
            System.out.println(this + "interrupted");
        }
    }

    private void doWork() throws InterruptedException {
        Thread.sleep(rand.nextInt(200));
    }
}
