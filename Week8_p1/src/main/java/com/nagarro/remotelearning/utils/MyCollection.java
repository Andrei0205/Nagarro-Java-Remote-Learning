package com.nagarro.remotelearning.utils;

import java.util.Collection;

public interface MyCollection<T extends Collection> {
    boolean containsAll(T collection);

    boolean addAll(T collection);
}