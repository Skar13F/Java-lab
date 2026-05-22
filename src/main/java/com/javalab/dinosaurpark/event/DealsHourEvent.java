package com.javalab.dinosaurpark.event;

import com.javalab.dinosaurpark.record.EventRecord;
import com.javalab.dinosaurpark.simulation.ParkState;

import java.time.LocalDateTime;
import java.util.Random;

public class DealsHourEvent implements SimulationEvent {

    private final double probability;

    public DealsHourEvent(double probability) {
        this.probability = probability;
    }

    @Override
    public String getName() {
        return "HORA_DE_OFERTAS";
    }

    @Override
    public double getProbability() {
        return probability;
    }

    @Override
    public void execute(ParkState state, Random rng) {

        state.setDealsHourActive(true);

        state.setCurrentDiscount(0.30);

        state.getDb().appendEvent(
                new EventRecord(
                        state.getCurrentStep(),
                        getName(),
                        "30% discount activated",
                        "ALL_VISITORS",
                        LocalDateTime.now()
                )
        );
    }
}