package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.utils.DateFormatter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DateFormatter customFormatter = new DateFormatter();
        System.out.println(customFormatter.formatDate(scanner.next()));
    }
}