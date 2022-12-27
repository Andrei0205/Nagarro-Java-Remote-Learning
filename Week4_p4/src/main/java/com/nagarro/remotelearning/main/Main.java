package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.utils.PigLatinEncoder;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        PigLatinEncoder pigLatinEncoder = new PigLatinEncoder();
        while (true) {
            System.out.println(pigLatinEncoder.encode(scanner.nextLine()));
        }
    }
}