package com.javalab.dinosaurpark;

import com.javalab.dinosaurpark.config.ParkConfig;
import com.javalab.dinosaurpark.simulation.SimulationEngine;

public class Main {
    public static void main(String[] args) {

        ParkConfig config = ParkConfig.getInstance();
        new SimulationEngine(config).run();
   }
}