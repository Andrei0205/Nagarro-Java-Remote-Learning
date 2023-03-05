package com.nagarro.remotelearning.utils;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class TrainTest {
    Set<Train> trains;

    @Before
    public void setUp() {
        trains = new HashSet<>();
    }

    @Test
    public void addDuplicateTrainsTest() {
        for (int index = 1; index <= 10; index++) {
            trains.add(new Train(12, "R-E", 4));
        }
        assertEquals(1, trains.size());
    }

}