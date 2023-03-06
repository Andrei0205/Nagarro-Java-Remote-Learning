package com.nagarro.remotelearning.utils;

public enum ClassType {
    INITIAL(""),
    RELOADED("com.nagarro.remotelearning.utils.MyClass"),
    SUBCLASS("com.nagarro.remotelearning.utils.SubClass");
    private String classType;

    ClassType(String classType) {
        this.classType = classType;
    }
    public String getClassType() {
        return classType;
    }
}
