package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.model.StringList;
import com.nagarro.remotelearning.model.IList;

public class Main {
    public static void main(String[] args) {
        IList<String> customList = new StringList();
        IList<String> customList2 = new StringList();
        customList.add("ASd");
        customList.add("12");
        customList.add("33");
        customList.add("44");
        customList2.add("12");
        customList2.add("33");
        customList2.add("44");
        if(customList.containsAll(customList2)){
            System.out.println("containsAll()");
        }
        if(customList.contains("44")){
            System.out.println("contains()");
        }

    }
}