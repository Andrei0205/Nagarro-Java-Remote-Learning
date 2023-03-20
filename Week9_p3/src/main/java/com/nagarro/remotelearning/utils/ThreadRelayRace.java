package com.nagarro.remotelearning.utils;

import java.util.concurrent.CountDownLatch;

public class ThreadRelayRace {
    private final CountDownLatch startSignal = new CountDownLatch(1);
    private final ThreadRaceContext threadRaceContext = new ThreadRaceContext();

    public void startRace() throws InterruptedException {
        for (int index = 1; index <= 10; index++) {
            new Thread(new ThreadRelayRaceTeam(index,"team"+index,threadRaceContext, startSignal)).start();
        }
        startSignal.countDown();
        startSignal.await();
        Thread.sleep(500);
        threadRaceContext.printResult();
    }

}
