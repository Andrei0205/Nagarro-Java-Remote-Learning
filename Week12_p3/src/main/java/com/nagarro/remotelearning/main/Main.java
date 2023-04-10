package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.utils.MyLock;

import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        MyLock.withLock(new ReentrantLock(),() -> System.out.println("Hello"));
    }
}