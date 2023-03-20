package com.nagarro.remotelearning.utils;

import java.util.concurrent.CountDownLatch;

public class ThreadRace {
    ThreadRaceContext raceContext;
    public ThreadRace(ThreadRaceContext raceContext) {
        this.raceContext = raceContext;
    }

    public void startRace() throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(1);

        for(int i = 0; i <= 10; i++)
            new Thread(new ThreadCompetitor(i,startSignal, doneSignal,raceContext)).start();
        startSignal.countDown();
        doneSignal.await();
        Thread.sleep(500);
        raceContext.printResult();
    }
}
