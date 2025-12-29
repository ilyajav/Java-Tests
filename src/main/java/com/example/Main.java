package com.example;

public class Main {

    private static final int LOOP_LIMIT = 5;

    public static void main(String[] args) {
        printWelcomeMessage();
        printLoopValues();
    }

    private static void printWelcomeMessage() {
        System.out.println("Welcome");
    }

    private static void printLoopValues() {
        for (int i = 1; i <= LOOP_LIMIT; i++) {
            System.out.println("i = " + i);
        }
    }
}