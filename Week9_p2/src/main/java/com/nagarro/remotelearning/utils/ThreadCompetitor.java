package com.nagarro.remotelearning.utils;

import java.util.concurrent.CountDownLatch;

public class ThreadCompetitor implements Runnable {
    private final int id;
    private final CountDownLatch startsignal;
    private final CountDownLatch endSignal;
    private final ThreadRaceContext threadRaceContext;

    public int getId() {
        return id;
    }

    public ThreadCompetitor(int id, CountDownLatch startsignal, CountDownLatch endSignal, ThreadRaceContext raceContext) {
        this.id = id;
        this.startsignal = startsignal;
        this.endSignal = endSignal;
        threadRaceContext = raceContext;
    }

    @Override
    public void run() {
        try {
            startsignal.await();
            doWork();
            endSignal.countDown();
        } catch (InterruptedException e) {
            System.out.println(this + "interrupted");
        }
    }

    private void doWork() throws InterruptedException {
        Thread.sleep(200);
        synchronized (this) {
            threadRaceContext.finish(this);
        }
    }

}
