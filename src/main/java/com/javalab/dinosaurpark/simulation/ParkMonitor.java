package com.javalab.dinosaurpark.simulation;

public class ParkMonitor {

    private ParkMonitor() {
    }

    public static void displaySnapshot(ParkState state) {

        System.out.println("===== PARK SNAPSHOT =====");

        System.out.println(
                "Step: " + state.getCurrentStep()
        );

        System.out.println(
                "Active tourists: "
                        + state.countActiveTourists()
        );

        System.out.println(
                "Dinosaurs in enclosure: "
                        + state.countDinosaursInEnclosure()
        );

        System.out.println(
                "Vehicles in use: "
                        + state.countVehiclesInUse()
        );

        System.out.println(
                "Revenue: $" + state.getTotalRevenue()
        );

        System.out.println(
                "Expenses: $" + state.getTotalExpenses()
        );

        System.out.println(
                "Power operational: "
                        + state.getPowerPlant()
                        .isOperational()
        );

        System.out.println(
                "Active events: "
                        + state.getActiveEventNames()
        );

        System.out.println("=========================");
    }
}