package com.nagarro.remotelearning.model;

import com.nagarro.remotelearning.app.CustomListException;

import java.util.List;
import java.util.ArrayList;

public class StringList implements IList {

    private List<String> stringList = new ArrayList<>();
    private List<String> recordOfOperations = new ArrayList<>();

    @Override
    public void add(Object element) {
        addRecord("add()");
        String elementToAdd = (String) element;
        if (elementToAdd == null) {
            throw new CustomListException("Null");
        }
        try {
            Integer.parseInt(elementToAdd);
        } catch (NumberFormatException e) {
            throw new CustomListException("Invalid number.");
        }
        stringList.add(elementToAdd);
    }

    @Override
    public Object get(int positon) {
        addRecord("get()");
        try {
            return stringList.get(positon);
        } catch (IndexOutOfBoundsException e) {
            throw new CustomListException("Index out of bounds.");
        }
    }

    @Override
    public boolean contains(Object element) {
        addRecord("contains()");
        for (String s : stringList) {
            if (element.equals(s)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(IList foreignList) {
        addRecord("containsAll()");
        for (String s : stringList) {
            if (!foreignList.contains(s)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        addRecord("size()");
        return stringList.size();
    }

    public List<String> getRecords() {
        return recordOfOperations;
    }

    private void addRecord(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("called ").append(s);
        recordOfOperations.add(stringBuilder.toString());
    }

}
