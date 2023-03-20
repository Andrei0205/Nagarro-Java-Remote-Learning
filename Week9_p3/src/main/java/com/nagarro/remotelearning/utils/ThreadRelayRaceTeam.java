package com.nagarro.remotelearning.utils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadRelayRaceTeam implements Runnable {
    private static final int NUMBER_OF_THREADS = 4;
    private final int teamId;
    private final String teamName;
    private final CountDownLatch latch = new CountDownLatch(4);
    private final CountDownLatch startSignal;
    private final ThreadRaceContext threadRaceContext;

    public int getId() {
        return teamId;
    }


    public ThreadRelayRaceTeam(int teamId, String teamName, ThreadRaceContext threadRaceContext, CountDownLatch startSignal) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.threadRaceContext = threadRaceContext;
        this.startSignal = startSignal;
    }

    @Override
    public void run() {
        try {
            startSignal.await();
        } catch (InterruptedException e) {
            System.out.println(this + "was interrupted");
        }

        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        for (int index = 1; index <= NUMBER_OF_THREADS; index++) {
            executor.submit(new ThreadCompetitor(latch));
        }
        executor.shutdown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            System.out.println(this + "was interrupted");
        }
        synchronized (this) {
            threadRaceContext.finish(this);
        }

    }
}
