package com.nagarro.remotelearning.tools;

public class PalindromeValidator implements Validator {
    @Override
    public boolean isValid(long number) {
        long reverse;
        long sum = 0;
        long temp;

        temp = number;
        while (number > 0) {
            reverse = number % 10;
            sum = (sum * 10) + reverse;
            number = number / 10;
        }
        if (temp == sum)
            return true;
        else
            return false;
    }
}