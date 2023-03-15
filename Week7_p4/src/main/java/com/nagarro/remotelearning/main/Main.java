package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.utils.Train;
import com.nagarro.remotelearning.utils.TrainGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        Map<Train, List<Integer>> trains = new HashMap<>();
        TrainGenerator trainGenerator = new TrainGenerator();
        trains = trainGenerator.getTrains();
        Train[] values = trains.keySet().toArray(new Train[trains.size()]);
        long date1 = System.currentTimeMillis();
        System.out.println(date1);
        System.out.println(trains.get(values[trains.size()-1]));
        long date2 = System.currentTimeMillis();
        System.out.println(date2-date1);
    }
}