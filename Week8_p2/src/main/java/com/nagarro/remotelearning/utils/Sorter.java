package com.nagarro.remotelearning.utils;

import java.util.*;

public class Sorter {
    public static <T extends Comparable> List<T> bubbleSort(List<T> list) {
        int staticElementIndex = 0;
        int subListStartIndex = 1;
        int mobileIndex;
        List<T> subList;
        for (T staticElement : list) {
            if (staticElement.equals(list.get(list.size() - 1))) {
                break;
            }
            subList = list.subList(subListStartIndex, list.size());
            mobileIndex = subListStartIndex;
            for (T mobileElement : subList) {

                if (staticElement.compareTo(mobileElement) > 0) {
                    T temporary = staticElement;
                    list.set(staticElementIndex, mobileElement);
                    staticElement = mobileElement;
                    list.set(mobileIndex, temporary);
                }
                mobileIndex++;
            }
            staticElementIndex++;
            subListStartIndex++;
        }

        return list;
    }
}
