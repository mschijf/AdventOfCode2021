package com.adventofcode.december15;

import java.util.List;

public class CavernMap {

    private Position[][] riskMap;

    public CavernMap(List<String> inputLineList) {
        riskMap = new Position[inputLineList.size()][];
        int lineCount = 0;
        for (String line: inputLineList) {
            riskMap[lineCount] = new Position[line.length()];
            for (int i=0; i < line.length(); ++i) {
                riskMap[lineCount][i] = new Position(line.charAt(i) - '0');
            }
            ++lineCount;
        }
        initNeighBours();
    }

    private void initNeighBours() {
        for (int row=0; row < riskMap.length; ++row) {
            for (int col=0; col < riskMap[row].length; ++col) {
                Position p = riskMap[row][col];
                if (legalPosition(row-1, col)) p.addNeighBour(riskMap[row-1][col]);
                if (legalPosition(row, col+1)) p.addNeighBour(riskMap[row][col+1]);
                if (legalPosition(row+1, col)) p.addNeighBour(riskMap[row+1][col]);
                if (legalPosition(row, col-1)) p.addNeighBour(riskMap[row][col-1]);
            }
        }
    }

    public int findShortestPath() {
        determinePathLengths();
        int lastValue = riskMap[riskMap.length-1][riskMap[riskMap.length-1].length-1].getRiskLevelFromStart();
        return lastValue - riskMap[0][0].getRiskLevelFromStart();
    }

    private void determinePathLengths() {
        riskMap[0][0].initRiskLevelFromStart();
        for (int row=0; row < riskMap.length; ++row) {
            for (int col=0; col < riskMap[row].length; ++col) {
                riskMap[row][col].calculateRiskLevelFromStart();
            }
        }
    }

    private boolean legalPosition(int row, int col) {
        return (row >= 0 && row < riskMap.length && col >= 0 && col < riskMap[row].length);
    }
}
