package com.javalab.dinosaurpark.simulation;

import com.javalab.dinosaurpark.config.ParkConfig;
import com.javalab.dinosaurpark.model.*;
import com.javalab.dinosaurpark.monitoring.ParkMonitor;
import com.javalab.dinosaurpark.zone.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationEngine {

    private final ParkState state;
    private final int totalSteps;
    private final int arrivalBatchSize;
    private final int monitoringInterval;
    private final ParkConfig config;

    public SimulationEngine(ParkConfig config) {
        this.config = config;
        Random rng = new Random();

        // =====================
        // TOURISTS
        // =====================
        List<Tourist> tourists = new ArrayList<>();

        for (int i = 1; i <= config.getTotalTourists(); i++) {
            tourists.add(new Tourist(i, "Tourist-" + i));
        }

        // =====================
        // DINOSAURS
        // =====================
        List<Dinosaur> dinosaurs = new ArrayList<>();

        for (int i = 1; i <= config.getCarnivores(); i++) {
            dinosaurs.add(new CarnivoreDinosaur(i, "Carnivore-" + i, "T-Rex"));
        }

        for (int i = 1; i <= config.getHerbivores(); i++) {
            dinosaurs.add(new HerbivoreDinosaur(i + 100, "Herbivore-" + i, "Triceratops"));
        }

        // =====================
        // WORKERS
        // =====================
        List<Worker> workers = new ArrayList<>();

        for (int i = 1; i <= config.getGuards(); i++) {
            workers.add(new Guard(i, "Guard-" + i, config.getDailySalary()));
        }

        for (int i = 1; i <= config.getTechnicians(); i++) {
            workers.add(new Technician(i + 100, "Technician-" + i, config.getDailySalary()));
        }

        // =====================
        // VEHICLES
        // =====================
        List<Vehicle> vehicles = new ArrayList<>();

        for (int i = 1; i <= config.getVehicleCount(); i++) {
            vehicles.add(
                    new Vehicle(
                            i,
                            "Vehicle-" + i,
                            config.getVehicleRepairSteps()
                    )
            );
        }

        // =====================
        // ZONES
        // =====================
        ArrivalZone arrivalZone = new ArrivalZone(
                "Arrival Zone",
                config.getArrivalMaxCapacity(),
                config.getTicketPrice()
        );

        CentralHub centralHub = new CentralHub(
                "Central Hub",
                100,
                config.getSouvenirPrice(),
                config.getSouvenirProb()
        );

        BathroomZone bathroomZone = new BathroomZone(
                "Bathroom Zone",
                config.getBathroomCapacity(),
                config.getBathroomDuration(),
                config.getSpaPrice(),
                config.getSpaProb()
        );

        PowerPlant powerPlant = new PowerPlant(
                config.getInitialEnergy(),
                config.getEnergyConsumption(),
                config.getFailureProb()
        );

        List<ObservationEnclosure> enclosures = new ArrayList<>();

        enclosures.add(new ObservationEnclosure(
                "Basic Enclosure",
                config.getBasicMaxVisitors(),
                config.getBasicEntryFee(),
                ExperienceType.BASIC,
                dinosaurs
        ));

        enclosures.add(new ObservationEnclosure(
                "Premium Enclosure",
                config.getPremiumMaxVisitors(),
                config.getPremiumEntryFee(),
                ExperienceType.PREMIUM,
                dinosaurs
        ));

        enclosures.add(new ObservationEnclosure(
                "VIP Enclosure",
                config.getVipMaxVisitors(),
                config.getVipEntryFee(),
                ExperienceType.VIP,
                dinosaurs
        ));

        // =====================
        // STATE
        // =====================
        this.state = new ParkState(
                tourists,
                dinosaurs,
                workers,
                vehicles,
                arrivalZone,
                centralHub,
                bathroomZone,
                powerPlant,
                enclosures,
                rng
        );

        this.totalSteps = config.getTotalSteps();
        this.arrivalBatchSize = config.getArrivalBatchSize();
        this.monitoringInterval = config.getMonitoringInterval();
    }

    public void run() {

        for (int step = 0; step < totalSteps; step++) {

            state.incrementStep();

            // =====================
            // A. ARRIVALS
            // =====================
            state.getArrivalZone().processBatch(arrivalBatchSize, state.getCurrentDiscount());

            // =====================
            // B. MOVEMENT
            // =====================
            List<Tourist> activeTourists = state.getTourists()
                    .stream()
                    .filter(t -> t.getStatus() == TouristStatus.IN_PARK)
                    .toList();

            for (Tourist tourist : activeTourists) {

                state.getCentralHub().visit(tourist, state.getRng(), state.getCurrentDiscount());
                state.getBathroomZone().tryEnter(tourist, state.getRng());

                List<ObservationEnclosure> enclosures = state.getEnclosures();

                if (!enclosures.isEmpty()) {

                    ObservationEnclosure enclosure =
                            enclosures.get(tourist.getId() % enclosures.size());

                    enclosure.visit(tourist, state.getRng(), state.getCurrentDiscount());
                }
            }

            // =====================
            // C. TICKS
            // =====================
            state.getBathroomZone().tick();
            state.getPowerPlant().tick(state.getRng());

            for (Vehicle v : state.getVehicles()) {
                v.tick();
            }

            // =====================
            // D. EVENTS (INTERMEDIO)
            // =====================
            state.clearActiveEvents();

            // Deals Hour
            if (state.getRng().nextDouble() < config.getProbDealsHour()) {
                state.setDealsHourActive(true);
                state.setCurrentDiscount(0.30);
                state.addActiveEvent("HORA_DE_OFERTAS");
            }

            // Vehicle Failure
            if (state.getRng().nextDouble() < config.getProbVehicleFailure()) {

                List<Vehicle> available = state.getVehicles().stream()
                        .filter(v -> v.getStatus() == VehicleStatus.AVAILABLE)
                        .toList();

                if (!available.isEmpty()) {
                    Vehicle v = available.get(
                            state.getRng().nextInt(available.size())
                    );

                    v.markBroken();
                    state.addActiveEvent("FALLA_VEHICULO");
                }
            }

            // =====================
            // E. WORKERS
            // =====================
            for (Worker worker : state.getWorkers()) {

                if (worker instanceof Guard guard) {
                    guard.recaptureEscapedDinosaurs(state.getDinosaurs());
                }

                if (worker instanceof Technician technician) {
                    technician.repairIfNeeded(
                            state.getPowerPlant(),
                            state.getVehicles()
                    );
                }

                state.addExpense(worker.getDailySalary());
            }

            // =====================
            // F. MONITOR
            // =====================
            if (state.getCurrentStep() % monitoringInterval == 0) {
                ParkMonitor.displaySnapshot(state);
            }
        }
    }
}