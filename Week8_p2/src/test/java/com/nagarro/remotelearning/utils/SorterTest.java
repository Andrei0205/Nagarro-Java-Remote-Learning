package com.nagarro.remotelearning.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SorterTest {
    @Test
    public void integerListTest() {
        List<Integer> unsortedList = new ArrayList<>();
        unsortedList.add(1089);
        unsortedList.add(469);
        unsortedList.add(12);
        unsortedList.add(5);
        unsortedList.add(78);
        unsortedList.add(3089);
        List<Integer> sortedList = Sorter.bubbleSort(unsortedList);
        assertEquals(Integer.valueOf(12), sortedList.get(1));
    }

}