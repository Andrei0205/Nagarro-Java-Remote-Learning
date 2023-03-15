package com.nagarro.remotelearning.utils;

import java.util.Collection;

public class GenericCollection<T> implements MyCollection{
    private final Collection<T> myCollection;
    private T[] elements;
    public GenericCollection(Collection<T> collection) {
        this.myCollection = collection;
        elements = myCollection.toArray((T[]) new Comparable[myCollection.size()]);
    }


    @Override
    public boolean containsAll(Collection collection) {
        boolean isEqual = false;
        for (Object item : collection) {
            isEqual = false;
            for(T element : elements) {
                if(item.equals(element)){
                    isEqual =true;
                }
            }
            if(!isEqual) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection collection) {
        myCollection.addAll(collection);
        elements = myCollection.toArray((T[]) new Comparable[myCollection.size()]);
        return true;
    }
}
