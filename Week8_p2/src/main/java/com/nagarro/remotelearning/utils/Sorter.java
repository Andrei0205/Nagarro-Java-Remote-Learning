package com.nagarro.remotelearning.utils;

import java.util.*;

public class Sorter {
    public <T extends Comparable<T>> Collection<T> bubbleSort(Collection<T> collectionToSort) {

        T[] arrayFromCollection = collectionToSort.toArray((T[]) new Comparable[collectionToSort.size()]);

        int lastElement = arrayFromCollection.length;
        for (int iterator = 0; iterator < lastElement - 1; iterator++)
            for (int itemToSwap = 0; itemToSwap < lastElement - iterator - 1; itemToSwap++)
                if (arrayFromCollection[itemToSwap].compareTo(arrayFromCollection[itemToSwap + 1]) > 0) {
                    swaptToNextElement(arrayFromCollection, itemToSwap);
                }

        collectionToSort = Arrays.asList(arrayFromCollection);

        return collectionToSort;
    }

    private  <T extends Comparable<T>> void swaptToNextElement(T[] arrayFromCollection, int itemToSwap) {
        T temp = arrayFromCollection[itemToSwap];
        arrayFromCollection[itemToSwap] = arrayFromCollection[itemToSwap + 1];
        arrayFromCollection[itemToSwap + 1] = temp;
    }
}
