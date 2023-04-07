package com.nagarro.remotelearning.utils;

import java.util.ArrayList;
import java.util.List;

public class Sorter {
    public <T extends Comparable> List<T> sortWithPriorityQueue(List<T> list) {
        MyPriorityQueue<T> priorityQueue = new MyPriorityQueue<>();
        List<T> sortedList = new ArrayList<>();
        for (T element : list) {
            priorityQueue.insert(element);
        }
        while (!priorityQueue.isEmpty()) {
            sortedList.add(priorityQueue.remove());
        }
        return sortedList;
    }

}
