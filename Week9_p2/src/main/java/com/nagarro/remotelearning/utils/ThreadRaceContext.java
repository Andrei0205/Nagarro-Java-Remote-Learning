package com.nagarro.remotelearning.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThreadRaceContext {
    private final List<Integer> log = Collections.synchronizedList(new ArrayList<>());

    public void finish(ThreadCompetitor competitor) {
        log.add(competitor.getId());
    }

    public void printResult() {
        for (int index : log) {
            System.out.println("Task no. " + index + " has finished");
        }

    }
}
