package com.nagarro.remotelearning.utils;

import java.util.concurrent.locks.ReentrantLock;

public class MyLock {
    public void withLock(ReentrantLock lock, Runnable runnable) {
        lock.lock();
        try {
            runnable.run();
        } finally {
            lock.unlock();
        }
    }
}
