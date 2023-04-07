package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.utils.ThreadRace;
import com.nagarro.remotelearning.utils.ThreadRaceContext;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ThreadRaceContext raceContext = new ThreadRaceContext();
        ThreadRace race = new ThreadRace(raceContext);
        race.startRace();
    }
}