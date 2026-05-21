package com.javalab.dinosaurpark.model;

public class Technician extends Worker {

    public Technician(int id, String name, double dailySalary) {
        super(id, name, dailySalary);
    }

    @Override
    public String getRole() {
        return "TECHNICIAN";
    }

    public void repairIfNeeded(PowerPlant plant) {
        if (!plant.isOperational()) {
            plant.repair();
        }
    }

    public void repairIfNeeded(PowerPlant plant,
                               List<Vehicle> vehicles) {

        if (!plant.isOperational()) {

            Optional<Vehicle> available = vehicles.stream()
                    .filter(v -> v.getStatus() == VehicleStatus.AVAILABLE)
                    .findFirst();

            if (available.isPresent()) {

                Vehicle vehicle = available.get();

                vehicle.use();

                plant.repair();

                vehicle.free();
            }
        }
    }
}