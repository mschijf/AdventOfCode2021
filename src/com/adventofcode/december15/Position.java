package com.adventofcode.december15;

import javafx.geometry.Pos;

import java.util.HashSet;
import java.util.Set;

public class Position {
    private int riskLevel;
    private Integer riskLevelFromStart;
    private Set<Position> neighBourSet;

    public Position(int v) {
        riskLevel = v;
        neighBourSet = new HashSet<>();
        riskLevelFromStart = null;
    }

    public void initRiskLevelFromStart() {
        this.riskLevelFromStart = riskLevel;
    }

    public void calculateRiskLevelFromStart() {
        if (hasRiskLeveFromStart())
            return;

        int min = 9999;
        for (Position neighbour: this.getNeighBourSet()) {
            if (neighbour.hasRiskLeveFromStart() && neighbour.getRiskLevelFromStart() < min)
                min = neighbour.getRiskLevelFromStart();
        }
        if (min < 9999) {
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
        if (this.hasRiskLeveFromStart() && (otherPath + this.riskLevel) < (this.getRiskLevelFromStart() - this.riskLevel)) {
            this.riskLevelFromStart = otherPath + riskLevel;
            updateRiskValueNeighBours(otherPath + riskLevel);
        }
    }


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
