package com.adventofcode.december15;

public class MainProgram {

    public static void main(String[] args) {
        new MainProgram();
    }

    //-----------------------------------------------------------

    private CavernMap cavernMap;

    private static final String monthNumber = "15";
    private static final String postfix = "1";

    public MainProgram() {
        String fileName = "input" + monthNumber + "_" + postfix;
        System.out.println("We run with file " + fileName);

        Input input = new Input(fileName);
        cavernMap = input.getBaseClass();

        long output = run();
        System.out.println("Puzzle output : " + output);
    }

    public long run() {
        long sum = cavernMap.findShortestPath();
        return sum;
    }
}
