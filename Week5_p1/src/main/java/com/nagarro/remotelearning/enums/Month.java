package com.nagarro.remotelearning.enums;

public enum Month {
    JANUARY(1),
    FEBRUARY(2),
    MARCH(3),
    APRIL(4),
    MAY(5),
    JUNE(6),
    JULY(7),
    AUGUST(8),
    SEPTEMBER(9),
    OCTOBER(10),
    NOVEMBER(11),
    DECEMBER(12);
    private int index;

    Month(int index) {
        this.index = index;
    }
    public static Month getMonthAtIndex(int givenIndex) {
        for(Month month : Month.values()) {
            if(givenIndex == month.getIndex()){
                return month;
            }
        }
        throw new UnsupportedOperationException("Please enter index <= 12");
    }

    public int getIndex() {
        return index;
    }
}
