package com.nagarro.remotelearning.utils;

import java.util.Collection;

public interface MyCollection <T extends Collection> {
    public  boolean containsAll(T collection);
    public boolean addAll(T collection);
}