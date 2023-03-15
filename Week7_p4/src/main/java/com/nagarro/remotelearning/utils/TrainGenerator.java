package com.nagarro.remotelearning.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;

public class TrainGenerator {
    Random random = new Random();

    public Map<Train, List<Integer>> getTrains() {
        HashMap<Train, List<Integer>> trains = new HashMap<>();
        for (int index = 0; index < 10000; index++) {
            trains.put(new Train(random.nextInt(10000), getRandomString(), random.nextInt(100)), getRandomList());
        }
        return trains;
    }

    private String getRandomString() {
        return RandomStringUtils.randomAlphabetic(3, 10);
    }

    private List<Integer> getRandomList() {
        List<Integer> list = new ArrayList<>();
        for (int index = 0; index < random.nextInt(100); index++) {
            list.add(random.nextInt(1000));
        }
        return list;
    }
}
