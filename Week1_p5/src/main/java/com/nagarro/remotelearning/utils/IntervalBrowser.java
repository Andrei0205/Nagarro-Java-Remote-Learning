package com.nagarro.remotelearning.utils;

public class IntervalBrowser {
    private long totalResults = 0;

    public void browseOn(long maxValue, long step, Validator validator) {
        for (long i = step; i <= maxValue; i += step)
            if (validator.isValid(i)) {
                totalResults++;
            }
    }

    public long getResults() {
        return totalResults;
    }
}
