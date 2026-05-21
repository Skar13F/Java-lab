package com.javalab.dinosaurpark.model;

public class PowerPlant {

    private boolean operational;

    public PowerPlant() {
        this.operational = true;
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
}