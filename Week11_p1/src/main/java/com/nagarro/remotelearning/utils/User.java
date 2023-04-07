package com.nagarro.remotelearning.utils;

import java.math.BigDecimal;

public class User {
    private final int id;
    private final String name;
    private final String username;
    private final BigDecimal balance;

    public User(int id, String name, String username, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", balance=" + balance +
                '}';
    }
}
