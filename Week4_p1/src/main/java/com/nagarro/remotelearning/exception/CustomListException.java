package com.nagarro.remotelearning.exception;

public class CustomListException extends RuntimeException {
    private final String msg;

    public CustomListException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMessage() {
        return msg;
    }
}
