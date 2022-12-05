package com.nagarro.remotelearning.utils;

public class PrimeNumbersPrinter {
    Validator primeChecker = new PrimeValidator();

    public void print(int lastNumber) {
        for (int i = 1; i <= lastNumber; i++) {
            System.out.print(i);
            primeChecker.validate(i);
            System.out.println();
        }
    }
}
