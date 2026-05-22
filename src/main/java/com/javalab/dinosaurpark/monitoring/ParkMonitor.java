package com.javalab.dinosaurpark.monitoring;

import com.javalab.dinosaurpark.model.Vehicle;
import com.javalab.dinosaurpark.model.VehicleStatus;
import com.javalab.dinosaurpark.simulation.ParkState;

public class ParkMonitor {

    private ParkMonitor() {
    }

    public static void displaySnapshot(ParkState state) {

        System.out.println();
        System.out.println("======================================");
        System.out.println("DINOSAUR PARK - SYSTEM MONITOR");
        System.out.println("======================================");

        System.out.println(
                "Step actual: "
                        + state.getCurrentStep()
        );

        System.out.println(
                "Turistas activos: "
                        + state.countActiveTourists()
        );

        System.out.println(
                "Dinosaurios en encierro: "
                        + state.countDinosaursInEnclosure()
        );

        System.out.println(
                "Energía disponible: "
                        + String.format(
                        "%.2f",
                        state.getPowerPlant().getEnergy()
                )
                        + "%"
        );

        System.out.println(
                "Eventos activos: "
                        + state.getActiveEventNames()
        );

        long unavailableVehicles = state.getVehicles()
                .stream()
                .filter(v ->
                        v.getStatus() == VehicleStatus.BROKEN
                                || v.getStatus() == VehicleStatus.IN_USE
                )
                .count();

        System.out.println(
                "Vehículos no disponibles: "
                        + unavailableVehicles
        );

        System.out.println(
                "Ingresos acumulados: $"
                        + String.format(
                        "%.2f",
                        state.getTotalRevenue()
                )
        );

        System.out.println(
                "Gastos acumulados: $"
                        + String.format(
                        "%.2f",
                        state.getTotalExpenses()
                )
        );

        System.out.println("======================================");
        System.out.println();
    }
}