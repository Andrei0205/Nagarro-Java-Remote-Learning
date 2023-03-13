package com.nagarro.remotelearning.utils;

import java.util.Collection;

public class GenericCollection<T> implements MyCollection{
    private Collection<T> myCollection;
    public GenericCollection(Collection<T> collection) {
        this.myCollection = collection;
    }

    @Override
    public boolean containsAll(Collection collection) {
        return myCollection.containsAll(collection);
    }

    @Override
    public boolean addAll(Collection collection) {
        return myCollection.addAll(collection);
    }
}
