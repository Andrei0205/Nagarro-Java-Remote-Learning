package com.nagarro.remotelearning.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class GenericCollectionTest {

    @Test
    public void containsAllTest() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 7, 9);
        GenericCollection<Integer> genericCollection = new GenericCollection<>(list);
        assertTrue(genericCollection.containsAll(Arrays.asList(2, 7, 9)));
    }

    @Test
    public void notContainsAllTest() {
        List<String> list = Arrays.asList("albastru", "galben", "mov");
        GenericCollection<String> genericCollection = new GenericCollection<>(list);
        assertFalse(genericCollection.containsAll(Arrays.asList("galben", "roz")));
    }

    @Test
    public void addAllTest() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        GenericCollection<Integer> genericCollection = new GenericCollection<>(list);
        List<Integer> list2 = new ArrayList<>();
        list2.add(233);
        list2.add(345);
        list2.add(100);
        assertTrue(genericCollection.addAll(list2));
    }


}