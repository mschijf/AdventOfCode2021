package com.adventofcode.december15;

public class MainProgram {

    public static void main(String[] args) {
        new MainProgram();
    }

    //-----------------------------------------------------------

    private CavernMap cavernMap;

    private static final String monthNumber = "15";
    private static final String examplePostfix = "example";
    private static final boolean test =  false;

    public MainProgram() {
        String fileName = "input" + monthNumber + "_" + (test ? examplePostfix : "1");
        System.out.println((test ? "TEST TEST TEST " : "") + "We run with file " + fileName);

        Input input = new Input(fileName);
        cavernMap = input.getBaseClass(true);

        long output = run();
        System.out.println("Puzzle output : " + output);
    }

    public long run() {
//        cavernMap.print();
        long sum = cavernMap.findShortestPath();
        return sum;
    }
}
