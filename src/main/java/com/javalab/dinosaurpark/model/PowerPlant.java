package com.javalab.dinosaurpark.model;

import java.util.Random;

public class PowerPlant {

    private double energy;
    private final double consumptionPerStep;
    private final double failureProbability;

    private boolean operational;

    public PowerPlant(double initialEnergy,
                      double consumptionPerStep,
                      double failureProbability) {

        this.energy = initialEnergy;
        this.consumptionPerStep = consumptionPerStep;
        this.failureProbability = failureProbability;
        this.operational = true;
    }

    public void tick(Random random) {

        if (!operational) {
            return;
        }

        energy -= consumptionPerStep;

        if (random.nextDouble() < failureProbability) {
            triggerFailure();
        }
    }

    public void triggerFailure() {
        operational = false;
    }

    public void fail() {
        operational = false;
    }

    public void repair() {
        operational = true;
    }

    public boolean isOperational() {
        return operational;
    }

    public double getEnergy() {
        return energy;
    }

    public double getConsumptionPerStep() {
        return consumptionPerStep;
    }

    public double getFailureProbability() {
        return failureProbability;
    }
}