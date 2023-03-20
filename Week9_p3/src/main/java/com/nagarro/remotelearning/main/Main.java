package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.utils.ThreadRelayRace;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ThreadRelayRace threadRelayRace = new ThreadRelayRace();
        threadRelayRace.startRace();

    }
}