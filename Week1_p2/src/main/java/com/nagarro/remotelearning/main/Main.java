package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.utils.PrimeNumbersPrinter;


public class Main {
    public static void main(String[] args) {
        PrimeNumbersPrinter printer = new PrimeNumbersPrinter();
        System.out.println("Enter an integer");
        printer.print(Integer.parseInt(args[0]));
    }
}