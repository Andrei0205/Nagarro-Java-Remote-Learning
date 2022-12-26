package com.nagarro.remotelearning.model;

import com.nagarro.remotelearning.app.CustomListException;

import java.util.List;
import java.util.ArrayList;

public class StringList implements IList {

    private List<String> stringList = new ArrayList<>();

    @Override
    public void add(Object element) {
        String elementToAdd = (String) element;
        try {
            checkIfCanBeAdded(elementToAdd);
        } catch (CustomListException e) {
            e.printStackTrace(System.out);
           // System.out.println(e.getMessage());
        }
        stringList.add(elementToAdd);
    }

    @Override
    public Object get(int positon) {
        try {
            checkPosition(positon);
        } catch (CustomListException e) {
            e.printStackTrace(System.out);
        }
        return stringList.get(positon);
    }

    @Override
    public boolean contains(Object element) {
        for (String s : stringList) {
            if (element.equals(s)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(IList foreignList) {
        for (String s : stringList) {
            if (!foreignList.contains(s)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return stringList.size();
    }

    private void checkIfCanBeAdded(String element) throws CustomListException {
        if (element == null) {
            throw new CustomListException("Null");
        }
        if (element == "") {
            throw new CustomListException("The added value is empty");
        }
        try {
            Integer.parseInt(element);
        } catch (NumberFormatException e) {
            throw new CustomListException("Invalid number.");
        }
    }

    private void checkPosition(int position) throws CustomListException {
        if (position >= size()) {
            throw new CustomListException("Index out of bounds.");
        }
    }

}
