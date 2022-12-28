package com.nagarro.remotelearning.app;

public class CustomListException extends RuntimeException {
    private String msg;

    public CustomListException() {
    }

    public CustomListException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMessage() {
        return msg;
    }
}
