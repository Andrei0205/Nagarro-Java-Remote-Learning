package com.nagarro.remotelearning.utils;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class ThreadCompetitor implements Runnable {
    private final int id;
    private final CountDownLatch startSignal;
    private final CountDownLatch endSignal;
    private final ThreadRaceContext threadRaceContext;


    public ThreadCompetitor(int id, CountDownLatch startSignal, CountDownLatch endSignal, ThreadRaceContext raceContext) {
        this.id = id;
        this.startSignal = startSignal;
        this.endSignal = endSignal;
        threadRaceContext = raceContext;
    }

    public int getId() {
        return id;
    }

    @Override
    public void run() {
        try {
            startSignal.await();
            doWork();
            endSignal.countDown();
        } catch (InterruptedException e) {
            System.out.println(this + "interrupted");
        }
    }

    private void doWork() throws InterruptedException {
        Thread.sleep(new Random().nextInt(2000));
        threadRaceContext.finish(this);
    }

}
