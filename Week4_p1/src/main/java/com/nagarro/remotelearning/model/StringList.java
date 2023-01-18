package com.nagarro.remotelearning.model;

import com.nagarro.remotelearning.exception.CustomListException;

import java.util.List;
import java.util.ArrayList;

public class StringList implements IList<String> {

    private int arraySize = 20;
    private Integer[] values = new Integer[arraySize];
    private int index = 0;
    private List<String> recordOfOperations = new ArrayList<>();

    @Override
    public void add(String element) {
        addRecord("add()");
        if (element == null) {
            throw new CustomListException("Null");
        }
        try {
            Integer.parseInt(element);
        } catch (NumberFormatException e) {
            throw new CustomListException("Invalid number.");
        }
        if (index == arraySize) {
            resize();
        }
        values[index] = Integer.parseInt(element);
        index++;
        resize();
    }

    @Override
    public String get(int positon) {
        addRecord("get()");
        if (positon >= index) {
            throw new CustomListException("Index out of bounds.");
        }
        return String.valueOf(values[positon]);
    }

    @Override
    public boolean contains(String element) {
        addRecord("contains()");
        for (Integer value : values) {
            if (value == Integer.parseInt(element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(IList<String> foreignList) {
        addRecord("containsAll()");
        for (Integer value : values) {
            if (!foreignList.contains(value.toString())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        addRecord("size()");
        return values.length;
    }

    public List<String> getRecords() {
        return recordOfOperations;
    }

    private void resize() {

        if (index == arraySize) {
            arraySize *= 2;
            Integer[] newArray = new Integer[arraySize];
            System.arraycopy(values, 0, newArray, 0, values.length);
            values = newArray;
        } else {
            Integer[] newArray = new Integer[index];
            arraySize = index;
            System.arraycopy(values, 0, newArray, 0, index);
            values = newArray;
        }
    }

    private void addRecord(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("called ").append(s);
        recordOfOperations.add(stringBuilder.toString());
    }

}
