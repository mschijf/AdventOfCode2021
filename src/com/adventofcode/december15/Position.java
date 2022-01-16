package com.adventofcode.december15;

import java.util.HashSet;
import java.util.Set;

public class Position {
    private int riskLevel;
    private Integer riskLevelFromStart;
    private Set<Position> neighBourSet;
    private int row;
    private int col;

    public Position(int row, int col, int v) {
        this.row = row;
        this.col = col;
        riskLevel = v;
        neighBourSet = new HashSet<>();
        riskLevelFromStart = null;
    }

    public int getRiskLevel() {
        return riskLevel;
    }

    public void initRiskLevelFromStart() {
        this.riskLevelFromStart = riskLevel;
    }

    public void calculateRiskLevelFromStart() {
        if (hasRiskLeveFromStart())
            return;

        int min = 999999;
        for (Position neighbour: this.getNeighBourSet()) {
            if (neighbour.hasRiskLeveFromStart() && neighbour.getRiskLevelFromStart() < min)
                min = neighbour.getRiskLevelFromStart();
        }
        if (min < 999999) {
            this.riskLevelFromStart = min + riskLevel;
            updateRiskValueNeighBours(min + riskLevel);
        }
    }

    private void updateRiskValueNeighBours(int otherPath) {
        for (Position neighbour: this.getNeighBourSet()) {
            neighbour.updateRiskLevelFromStart(otherPath);
        }
    }

    private void updateRiskLevelFromStart(int otherPath) {
        if (this.hasRiskLeveFromStart() && (otherPath + this.riskLevel) < (this.getRiskLevelFromStart())) {
            this.riskLevelFromStart = otherPath + riskLevel;
            updateRiskValueNeighBours(otherPath + riskLevel);
        }
    }

//    private static int countV = 0;
//    public void verify() {
//        int min = 999999;
//        for (Position neighbour: this.getNeighBourSet()) {
//            if (neighbour.getRiskLevelFromStart() < min)
//                min = neighbour.getRiskLevelFromStart();
//        }
//        if (riskLevel + min != riskLevelFromStart) {
//            countV++;
//            System.out.println(" ["+row+"]["+col+"] : " + countV);
//        }
//    }

    public int getRiskLevelFromStart() {
        return riskLevelFromStart;
    }

    public boolean hasRiskLeveFromStart() {
        return riskLevelFromStart != null;
    }

    public void addNeighBour(Position neighbour) {
        neighBourSet.add(neighbour);
    }

    public Set<Position> getNeighBourSet() {
        return neighBourSet;
    }

}
