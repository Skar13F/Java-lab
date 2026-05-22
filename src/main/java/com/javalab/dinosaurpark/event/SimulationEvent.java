package com.javalab.dinosaurpark.event;

import com.javalab.dinosaurpark.simulation.ParkState;

import java.util.Random;

public interface SimulationEvent {

    String getName();

    double getProbability();

    void execute(ParkState state, Random rng);
}