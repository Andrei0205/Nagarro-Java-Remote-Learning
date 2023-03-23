package com.nagarro.remotelearning.utils;

import java.util.concurrent.CountDownLatch;

public class ThreadRelayRace {
    public static final int NUMBER_OF_TEAMS = 10;

    public void startRace() throws InterruptedException {
        final CountDownLatch startSignal = new CountDownLatch(1);
        final CountDownLatch finalSignal = new CountDownLatch(NUMBER_OF_TEAMS);
        final ThreadRaceContext threadRaceContext = new ThreadRaceContext();

        for (int index = 1; index <= NUMBER_OF_TEAMS; index++) {
            new Thread(new ThreadRelayRaceTeam(index, threadRaceContext, startSignal, finalSignal)).start();
        }
        startSignal.countDown();
        finalSignal.await();
        threadRaceContext.printResult();
    }

}
