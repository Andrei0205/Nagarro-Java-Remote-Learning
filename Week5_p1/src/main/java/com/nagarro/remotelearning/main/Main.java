package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.utils.CustomFormatter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CustomFormatter customFormatter = new CustomFormatter();
        customFormatter.format(scanner.next());
    }
}