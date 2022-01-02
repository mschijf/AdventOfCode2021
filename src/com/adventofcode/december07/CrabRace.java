package com.adventofcode.december07;

import java.util.List;
import java.util.NoSuchElementException;

public class CrabRace {

    public static void main(String[] args) {
        CrabRace pp = new CrabRace();
        long output = pp.run();
        System.out.println("Puzzle output : " + output);
    }

    //-----------------------------------------------------------

    private List<Integer> crabPositionList;

    public CrabRace() {
//        Input input = new Input("input07_example");
        Input input = new Input("input07_1");
        crabPositionList = input.getNumbers();
        printInput();
    }

    public long run() {
        int minPos = crabPositionList.stream().mapToInt(v->v).min().orElseThrow(NoSuchElementException::new);
        int maxPos = crabPositionList.stream().mapToInt(v->v).max().orElseThrow(NoSuchElementException::new);

        int minFuelCosts = 999999999;
        for (int pos=minPos; pos <=maxPos; ++pos) {
            minFuelCosts = Integer.min(minFuelCosts, calcFuelCosts(pos));
        }
        return minFuelCosts;
    }

    private int calcFuelCosts(int position) {
        int sum = 0;
        for (Integer crabPosition: crabPositionList) {
            int dist = Math.abs(crabPosition - position);
            sum += dist;
//            sum += (dist * (dist + 1))/2;
        }
        return sum;
    }

    private void printInput() {
        for (Integer b: crabPositionList)
            System.out.print(b + " ");
        System.out.println();
    }

    //---------------------------------------------------------------------------------------------------------------------

    public static long sumArray(long[] serie) {
        long sum = 0;
        for (long value : serie) {
            sum += value;
        }
        return sum;
    }

    //---------------------------------------------------------------------------------------------------------------------

    /* Checks if a string is empty ("") or null. */
    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    /* Counts how many times the substring appears in the larger string. */
    public static int countMatches(String text, String str)
    {
        if (isEmpty(text) || isEmpty(str)) {
            return 0;
        }

        int index = 0, count = 0;
        while (true)
        {
            index = text.indexOf(str, index);
            if (index != -1)
            {
                count ++;
                index += str.length();
            }
            else {
                break;
            }
        }

        return count;
    }
}
