package com.nagarro.remotelearning.enums;

public enum Day {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7);

    private final int index;

    Day(int index) {
        this.index = index;
    }

    public static Day getDayAtIndex(int givenIndex) {
        for(Day day : Day.values()) {
            if(givenIndex == day.getIndex()){
                return day;
            }
        }
        throw new UnsupportedOperationException("Please enter index <= 7");
    }

    public int getIndex() {
        return index;
    }
}
