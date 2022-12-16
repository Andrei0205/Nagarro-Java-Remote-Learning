package com.nagarro.remotelearning.app;

public class Frog extends Amphibian {
    @Override
    public void play() {
        System.out.println("Frog");
    }
    @Override
    public void swim(String name){
        System.out.println(name + " Swim + jump");
    }
}
