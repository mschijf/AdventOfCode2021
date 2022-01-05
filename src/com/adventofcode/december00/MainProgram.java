package com.adventofcode.december00;

import java.util.List;

public class MainProgram {

    public static void main(String[] args) {
        MainProgram pp = new MainProgram();
        long output = pp.run();
        System.out.println("Puzzle output : " + output);
    }

    //-----------------------------------------------------------

    private List<BaseClass> baseClassList;
    private BaseClass baseClass;

    private static final String monthNumber = "12";
    private static final String postfix = "1";

    public MainProgram() {
        Input input = new Input("input" + monthNumber + "_" + postfix);
        baseClassList = input.getBaseClassLines();
        baseClass = input.getBaseClass();
    }

    public long run() {
        long sum = 0;
        return sum;
    }
}
