package com.nagarro.remotelearning.utils;

import java.util.ArrayList;
import java.util.List;

public class Tank {
    private List<Object> stack = new ArrayList<>();

    public void push() {
        stack.add(new Object());
    }

    public void pop() {
        if (!stack.isEmpty()) {
            stack.remove(stack.size() - 1);
        } else {
            System.out.println("Stack is empty");
        }
    }

    @Override
    protected void finalize() throws Throwable {
        if (stack.isEmpty()) {
            System.out.println("Can be collected");
        } else {
            System.out.println("Stack is in use");
            return;
        }
        super.finalize();
    }
}
