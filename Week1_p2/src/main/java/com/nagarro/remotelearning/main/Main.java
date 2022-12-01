package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.tools.PrimeNumbersPrinter;


public class Main {
    static PrimeNumbersPrinter printer = new PrimeNumbersPrinter();

    public static void main(String[] args) {
        System.out.println("Enter an integer");

        printer.print(Integer.parseInt(args[0]));
    }
}