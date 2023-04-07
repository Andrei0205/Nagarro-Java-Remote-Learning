package com.nagarro.remotelearning.utils;

import java.util.concurrent.*;

public class ThreadRelayRaceTeam implements Runnable {
    private static final int NUMBER_OF_THREADS = 4;
    private static final int THREADS_EXECUTED_IN_SAME_TIME = 1;
    private final int teamId;
    private final CountDownLatch startSignal;
    private final CountDownLatch finalSignal;
    private final ThreadRaceContext threadRaceContext;

    public ThreadRelayRaceTeam(int teamId, ThreadRaceContext threadRaceContext,
                               CountDownLatch startSignal, CountDownLatch finalSignal) {
        this.teamId = teamId;
        this.threadRaceContext = threadRaceContext;
        this.startSignal = startSignal;
        this.finalSignal = finalSignal;
    }

    public int getId() {
        return teamId;
    }

    @Override
    public void run() {
        try {
            startSignal.await();
        } catch (InterruptedException e) {
            System.out.println(this + "was interrupted");
        }

        final CountDownLatch latch = new CountDownLatch(NUMBER_OF_THREADS);
        final Semaphore semaphore = new Semaphore(THREADS_EXECUTED_IN_SAME_TIME);

        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        for (int index = 1; index <= NUMBER_OF_THREADS; index++) {
            executor.submit(new ThreadCompetitor(semaphore, latch));
        }
        executor.shutdown();

        try {
            latch.await();
            finalSignal.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        threadRaceContext.finish(this);
    }
}
