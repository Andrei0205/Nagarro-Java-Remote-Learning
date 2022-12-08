package com.nagarro.remotelearning.utils;

public class Tank {
    private int capacity = 0;

    public void fillWith(int amount) {
        capacity += amount;
    }

    public void emptyWith(int amount) {
        if (amount > capacity) {
            System.out.println("Do not have enough");
        } else {
            capacity -= amount;
        }
    }

    private boolean isEmpty() {
        return (capacity == 0);
    }

    @Override
    protected void finalize()  {
        if(isEmpty()){
            System.out.println("Can be collected");
        } else {
            System.out.println("Tank is in use");
        }
    }
}
