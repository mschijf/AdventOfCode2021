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

    public MainProgram() {
//        Input input = new Input("input05_example");
        Input input = new Input("input05_1");
        baseClassList = input.getChunkLines();
    }

    public long run() {
        long sum = 0;
        return sum;
    }
}
