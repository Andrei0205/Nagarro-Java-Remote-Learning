package com.nagarro.remotelearning.utils;

public class PrimeValidator implements Validator{

    @Override
    public void validate(int number) {
        if (number <= 1)
            return;

        for (int i = 2; i < number; i++)
            if (number % i == 0)
                return;

        System.out.print("-PRIME");
    }
}
