package com.nagarro.remotelearning.utils;

import java.util.concurrent.CountDownLatch;

public class ThreadRace {
    private static final int NUMBER_OF_THREADS = 10;
    ThreadRaceContext raceContext;
    public ThreadRace(ThreadRaceContext raceContext) {
        this.raceContext = raceContext;
    }

    public void startRace() throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(NUMBER_OF_THREADS);

        for(int i = 0; i <= 10; i++)
            new Thread(new ThreadCompetitor(i,startSignal, doneSignal,raceContext)).start();
        startSignal.countDown();
        doneSignal.await();
        raceContext.printResult();
    }
}
