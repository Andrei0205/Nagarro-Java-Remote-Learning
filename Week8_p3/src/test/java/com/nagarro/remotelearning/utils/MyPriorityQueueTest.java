package com.nagarro.remotelearning.utils;

import org.junit.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MyPriorityQueueTest {


    @Test
    public void integerPriorityQueueTest() {
        MyPriorityQueue<Integer> myPriorityQueue = new MyPriorityQueue<>();
        myPriorityQueue.insert(23);
        myPriorityQueue.insert(124);
        myPriorityQueue.insert(1);
        assertEquals(Integer.valueOf(124), myPriorityQueue.head());
    }

    @Test
    public void stringPriorityQueueTest() {
        MyPriorityQueue<String> myPriorityQueue = new MyPriorityQueue<>();
        myPriorityQueue.insert("Ana");
        myPriorityQueue.insert("Zebra");
        myPriorityQueue.insert("Bac");
        myPriorityQueue.remove();
        assertEquals("Bac", myPriorityQueue.head());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void sizeTest() {
        MyPriorityQueue<Double> myPriorityQueue = new MyPriorityQueue<>(4);
        myPriorityQueue.insert(34.9);
        myPriorityQueue.insert(1674.54);
        myPriorityQueue.insert(235.1);
        myPriorityQueue.insert(11.2);
        myPriorityQueue.insert(0.4);

    }

    @Test
    public void clearTest() {
        MyPriorityQueue<Integer> myPriorityQueue = new MyPriorityQueue<>();
        myPriorityQueue.insert(54);
        myPriorityQueue.insert(4367);
        myPriorityQueue.insert(849);
        myPriorityQueue.clear();
        assertTrue(myPriorityQueue.isEmpty());
    }

    @Test
    public void removeTest() {
        MyPriorityQueue<Integer> myPriorityQueue = new MyPriorityQueue<>();
        myPriorityQueue.insert(54);
        myPriorityQueue.insert(4367);
        myPriorityQueue.insert(849);
        myPriorityQueue.insert(1009);
        myPriorityQueue.insert(207);
        myPriorityQueue.remove();
        myPriorityQueue.remove();
        myPriorityQueue.remove();
        myPriorityQueue.remove();
        assertEquals(Integer.valueOf(54), myPriorityQueue.remove());

    }

    @Test
    public void compareTest() {
        MyPriorityQueue<Integer> myPriorityQueue = new MyPriorityQueue<>();
        myPriorityQueue.insert(54);
        myPriorityQueue.insert(4367);
        myPriorityQueue.insert(849);
        MyPriorityQueue<Integer> myPriorityQueue2 = new MyPriorityQueue<>();
        myPriorityQueue2.insert(1009);
        myPriorityQueue2.insert(207);
        assertTrue(myPriorityQueue.compareTo(myPriorityQueue2) > 0);
    }

    @Test
    public void sortingTest() {
        List<Integer> unsortedList = new ArrayList<>();
        unsortedList.add(79);
        unsortedList.add(44);
        unsortedList.add(1);
        unsortedList.add(1089);
        unsortedList.add(269);
        List<Integer> sortedList = Sorter.sortWithPriorityQueue(unsortedList);
        assertEquals(Integer.valueOf(1089), sortedList.get(0));
    }

}