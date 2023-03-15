package com.nagarro.remotelearning.utils;

import java.util.ArrayList;
import java.util.List;

public class MyPriorityQueue<E extends Comparable> implements Comparable<MyPriorityQueue<E>> {

    private static final int DEFAULT_MAXIMUM_SIZE = 10000;
    private final List<E> data = new ArrayList<>();

    private final int maxSize;

    public MyPriorityQueue() {
        maxSize = DEFAULT_MAXIMUM_SIZE;
    }

    public MyPriorityQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public void insert(E element) {
        if (data.size() == maxSize) {
            throw new IndexOutOfBoundsException();
        }
        data.add(element);
        int currentItem = data.size() - 1;


        while (currentItem > 0) {
            int priorityItem = (currentItem - 1) / 2;
            if (data.get(currentItem).compareTo(data.get(priorityItem)) <= 0) {
                break;
            }
            swapElements(data,currentItem,priorityItem);
            currentItem = priorityItem;
        }
    }

    public E remove() {
        int lastItem = data.size() - 1;
        E frontItem = data.get(0);
        data.set(0, data.get(lastItem));
        data.remove(lastItem);

        --lastItem;
        int priorityItem = 0;

        while (true) {
            int currentItem = priorityItem * 2 + 1;
            if (currentItem > lastItem)
                break;
            int rightCurrent = currentItem + 1;
            if (rightCurrent <= lastItem && data.get(rightCurrent).compareTo(data.get(currentItem)) > 0)
                currentItem = rightCurrent;
            if (data.get(priorityItem).compareTo(data.get(currentItem)) >= 0)
                break;
            swapElements(data,priorityItem,currentItem);
        }
        return frontItem;
    }

    public E head() {
        return (data.isEmpty()) ? null : data.get(0);
    }

    public void clear() {
        data.clear();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public int compareTo(MyPriorityQueue o) {
        return this.head().compareTo(o.head());
    }

    private void swapElements(List<E> data,int element1,int element2) {
        E temporary = data.get(element1);
        data.set(element1, data.get(element2));
        data.set(element2, temporary);
        element1 = element2;
    }
}
