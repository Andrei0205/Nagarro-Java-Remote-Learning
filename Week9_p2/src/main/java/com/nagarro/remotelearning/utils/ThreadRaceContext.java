package com.nagarro.remotelearning.utils;

import java.util.Vector;

public class ThreadRaceContext {
    private final Vector<Integer> log = new Vector<>();

    public void finish(ThreadCompetitor competitor) {
        log.add(competitor.getId());
    }

    public void printResult() {
        for (int index : log) {
            System.out.println("Task no. " + index + " has finished");
        }

    }
}
