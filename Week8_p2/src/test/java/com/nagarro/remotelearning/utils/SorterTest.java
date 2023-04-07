package com.nagarro.remotelearning.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SorterTest {
    @Test
    public void integerListTest() {
        Sorter sorter = new Sorter();
        List<Integer> unsortedList = new ArrayList<>();
        unsortedList.add(1089);
        unsortedList.add(469);
        unsortedList.add(12);
        unsortedList.add(5);
        unsortedList.add(78);
        unsortedList.add(3089);
        List<Integer> sortedList = (List<Integer>) sorter.bubbleSort(unsortedList);
        List<Integer> expectedList = new ArrayList<>(Arrays.asList(5,12,78,469,1089,3089));
        assertEquals(expectedList,sortedList);
    }

}