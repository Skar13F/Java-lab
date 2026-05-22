package com.javalab.dinosaurpark.model;

public class Vehicle {

    private final int id;
    private final String name;

    private VehicleStatus status;

    private final int repairSteps;
    private int repairCountdown;

    public Vehicle(
            int id,
            String name,
            int repairSteps
    ) {

        this.id = id;
        this.name = name;
        this.repairSteps = repairSteps;

        this.status = VehicleStatus.AVAILABLE;
        this.repairCountdown = 0;
    }

    public void use() {

        if (status == VehicleStatus.AVAILABLE) {
            status = VehicleStatus.IN_USE;
        }
    }

    public void free() {

        if (status == VehicleStatus.IN_USE) {
            status = VehicleStatus.AVAILABLE;
        }
    }

    public void markBroken() {
        if (status != VehicleStatus.BROKEN) {
            status = VehicleStatus.BROKEN;
            repairCountdown = repairSteps;
        }
    }

    public void tick() {

        if (status == VehicleStatus.BROKEN) {

            if (repairCountdown > 0) {
                repairCountdown--;
            }

            if (repairCountdown <= 0) {

                status = VehicleStatus.AVAILABLE;
                repairCountdown = 0;
            }
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public int getRepairCountdown() {
        return repairCountdown;
    }
}