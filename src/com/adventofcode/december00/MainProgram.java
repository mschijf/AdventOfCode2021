package com.adventofcode.december00;

import java.util.List;

public class MainProgram {

    public static void main(String[] args) {
        new MainProgram();
    }

    //-----------------------------------------------------------

    private BaseClass baseClass;

    private static final String monthNumber = "12";
    private static final String postfix = "1";

    public MainProgram() {
        String fileName = "input" + monthNumber + "_" + postfix;
        System.out.println("We run with file " + fileName);

        Input input = new Input(fileName);
        baseClass = input.getBaseClass();

        long output = run();
        System.out.println("Puzzle output : " + output);
    }

    public long run() {
        long sum = 0;
        return sum;
    }
}
