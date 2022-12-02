package com.nagarro.remotelearning.tools;

public class IntervalBrowser {
    private long i;
    private long totalResults = 0;

    public void browseOn(long maxValue, long step, Validator validator) {
        for (long i = step; i <= maxValue; i += step)
            if (validator.isValid(i) == true) {
                totalResults++;
            }
    }

    public long getResults() {
        return totalResults;
    }
}
