package com.javalab.dinosaurpark.simulation;

import com.javalab.dinosaurpark.model.Guard;
import com.javalab.dinosaurpark.model.Technician;
import com.javalab.dinosaurpark.model.Tourist;
import com.javalab.dinosaurpark.model.TouristStatus;
import com.javalab.dinosaurpark.model.Worker;
import com.javalab.dinosaurpark.zone.ObservationEnclosure;

import java.util.List;

public class SimulationEngine {

    private final ParkState state;
    private final int totalSteps;
    private final int arrivalBatchSize;
    private final int monitoringInterval;

    public SimulationEngine(ParkState state,
                            int totalSteps,
                            int arrivalBatchSize,
                            int monitoringInterval) {

        this.state = state;
        this.totalSteps = totalSteps;
        this.arrivalBatchSize = arrivalBatchSize;
        this.monitoringInterval = monitoringInterval;
    }

    public void run() {

        for (int step = 0; step < totalSteps; step++) {

            state.incrementStep();

            // A. LLEGADAS
            state.getArrivalZone()
                    .processBatch(arrivalBatchSize);

            // B. MOVIMIENTO
            List<Tourist> activeTourists = state.getTourists()
                    .stream()
                    .filter(t -> t.getStatus() == TouristStatus.IN_PARK)
                    .toList();

            for (Tourist tourist : activeTourists) {

                state.getCentralHub()
                        .visit(tourist, state.getRng());

                state.getBathroomZone()
                        .tryEnter(tourist, state.getRng());

                List<ObservationEnclosure> enclosures =
                        state.getEnclosures();

                if (!enclosures.isEmpty()) {

                    ObservationEnclosure enclosure =
                            enclosures.get(
                                    tourist.getId()
                                            % enclosures.size()
                            );

                    enclosure.visit(
                            tourist,
                            state.getRng()
                    );
                }
            }

            // C. TICKS
            state.getBathroomZone().tick();

            state.getPowerPlant()
                    .tick(state.getRng());

            // D. EVENTOS
            state.clearActiveEvents();

            // E. WORKERS
            for (Worker worker : state.getWorkers()) {

                if (worker instanceof Guard guard) {

                    guard.recaptureEscapedDinosaurs(
                            state.getDinosaurs()
                    );
                }

                if (worker instanceof Technician technician) {

                    technician.repairIfNeeded(
                            state.getPowerPlant(),
                            state.getVehicles()
                    );
                }

                state.addExpense(
                        worker.getDailySalary()
                );
            }

            // F. MONITOR
            if (state.getCurrentStep()
                    % monitoringInterval == 0) {

                ParkMonitor.displaySnapshot(state);
            }
        }
    }
}