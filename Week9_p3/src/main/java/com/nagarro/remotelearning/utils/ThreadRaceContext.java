package com.nagarro.remotelearning.utils;

import java.util.Vector;

public class ThreadRaceContext {
    private final Vector<Integer> log = new Vector<>();

    public void finish(ThreadRelayRaceTeam competitor) {
        log.add(competitor.getId());
    }

    public void printResult() {
        for (int index : log) {
            System.out.println("Team " + index + " has finished");
        }

    }
}
