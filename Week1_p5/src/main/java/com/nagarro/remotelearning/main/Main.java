package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.utils.IntervalBrowser;
import com.nagarro.remotelearning.utils.PalindromeValidator;
import com.nagarro.remotelearning.utils.Validator;

public class Main {
    static IntervalBrowser browser = new IntervalBrowser();
    static Validator validator = new PalindromeValidator();

    public static void main(String[] args) {
        browser.browseOn(100000, 109, validator);
        //browser.browseOn(Long.MAX_VALUE/2,10000019,validator);
        System.out.println(browser.getResults());
    }
}