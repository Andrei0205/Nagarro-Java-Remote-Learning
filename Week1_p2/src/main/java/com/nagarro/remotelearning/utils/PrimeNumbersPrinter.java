package com.nagarro.remotelearning.utils;

public class PrimeNumbersPrinter {
    Validator primeChecker = new PrimeValidator();

    public void print(int lastNumber) {
        for (int i = 1; i <= lastNumber; i++) {
            System.out.print(i);
            if (primeChecker.validate(i)) {
                System.out.print("-PRIME");
            }
            System.out.println();
        }
    }
}
