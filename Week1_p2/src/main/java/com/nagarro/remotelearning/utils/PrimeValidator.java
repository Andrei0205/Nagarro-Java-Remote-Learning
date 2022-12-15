package com.nagarro.remotelearning.utils;

public class PrimeValidator implements Validator {

    @Override
    public boolean validate(int number) {
        if (number <= 1) {
            return false;
        }

        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
