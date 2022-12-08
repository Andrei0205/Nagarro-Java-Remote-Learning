package com.nagarro.remotelearning.main;


import com.nagarro.remotelearning.utils.Tank;

public class Main {
    public static void main(String[] args) {
        Tank tank1 = new Tank();
        Tank tank2 = new Tank();
        tank1.fillWith(29);
        tank1.emptyWith(29);
        tank1 = tank2;
        System.gc();

    }
}