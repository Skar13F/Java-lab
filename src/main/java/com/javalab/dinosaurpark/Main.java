package com.javalab.dinosaurpark;

import com.javalab.dinosaurpark.config.ParkConfig;
import com.javalab.dinosaurpark.persistence.DatabaseService;
import com.javalab.dinosaurpark.simulation.SimulationEngine;

public class Main {

    public static void main(String[] args) {

        ParkConfig config =
                ParkConfig.getInstance();

        DatabaseService db =
                new DatabaseService(
                        config.getString(
                                "db.path",
                                "./data/parkdb"
                        )
                );

        SimulationEngine engine =
                new SimulationEngine(config);

        engine.run();

        db.close();
    }
}