package com.javalab.dinosaurpark.event;

import com.javalab.dinosaurpark.model.Vehicle;
import com.javalab.dinosaurpark.model.VehicleStatus;
import com.javalab.dinosaurpark.record.EventRecord;
import com.javalab.dinosaurpark.simulation.ParkState;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class VehicleFailureEvent implements SimulationEvent {

    private final double probability;

    public VehicleFailureEvent(double probability) {
        this.probability = probability;
    }

    @Override
    public String getName() {
        return "FALLA_VEHICULO";
    }

    @Override
    public double getProbability() {
        return probability;
    }

    @Override
    public void execute(ParkState state, Random rng) {

        List<Vehicle> availableVehicles =
                state.getVehicles()
                        .stream()
                        .filter(v ->
                                v.getStatus()
                                        == VehicleStatus.AVAILABLE
                        )
                        .toList();

        if (availableVehicles.isEmpty()) {
            return;
        }

        Vehicle vehicle =
                availableVehicles.get(
                        rng.nextInt(
                                availableVehicles.size()
                        )
                );

        vehicle.markBroken();

        state.getDb().appendEvent(
                new EventRecord(
                        state.getCurrentStep(),
                        getName(),
                        "Vehicle broken: " + vehicle.getName(),
                        vehicle.getName(),
                        LocalDateTime.now()
                )
        );
    }
}