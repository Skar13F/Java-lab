package com.javalab.dinosaurpark.simulation;

import com.javalab.dinosaurpark.model.*;
import com.javalab.dinosaurpark.zone.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParkState {

    private final List<Tourist> tourists;
    private final List<Dinosaur> dinosaurs;
    private final List<Worker> workers;
    private final List<Vehicle> vehicles;

    private final ArrivalZone arrivalZone;
    private final CentralHub centralHub;
    private final BathroomZone bathroomZone;
    private final PowerPlant powerPlant;
    private final List<ObservationEnclosure> enclosures;

    private final Random rng;

    private final List<String> activeEventNames;

    private double totalRevenue;
    private double totalExpenses;

    private int currentStep;

    private boolean dealsHourActive;
    private double currentDiscount;

    public ParkState(List<Tourist> tourists,
                     List<Dinosaur> dinosaurs,
                     List<Worker> workers,
                     List<Vehicle> vehicles,
                     ArrivalZone arrivalZone,
                     CentralHub centralHub,
                     BathroomZone bathroomZone,
                     PowerPlant powerPlant,
                     List<ObservationEnclosure> enclosures,
                     Random rng) {

        this.tourists = tourists;
        this.dinosaurs = dinosaurs;
        this.workers = workers;
        this.vehicles = vehicles;

        this.arrivalZone = arrivalZone;
        this.centralHub = centralHub;
        this.bathroomZone = bathroomZone;
        this.powerPlant = powerPlant;
        this.enclosures = enclosures;

        this.rng = rng;

        this.activeEventNames = new ArrayList<>();

        this.totalRevenue = 0.0;
        this.totalExpenses = 0.0;

        this.currentStep = 0;

        this.dealsHourActive = false;
        this.currentDiscount = 0.0;
    }

    public void incrementStep() {
        currentStep++;
    }

    public int countActiveTourists() {

        return (int) tourists.stream()
                .filter(t -> t.getStatus() == TouristStatus.IN_PARK)
                .count();
    }

    public int countDinosaursInEnclosure() {

        return (int) dinosaurs.stream()
                .filter(d -> d.getStatus() == DinosaurStatus.IN_ENCLOSURE)
                .count();
    }

    public int countVehiclesInUse() {

        return (int) vehicles.stream()
                .filter(v -> v.getStatus() == VehicleStatus.IN_USE)
                .count();
    }

    public void addRevenue(double amount) {
        totalRevenue += amount;
    }

    public void addExpense(double amount) {
        totalExpenses += amount;
    }

    public void addActiveEvent(String eventName) {
        activeEventNames.add(eventName);
    }

    public void clearActiveEvents() {
        activeEventNames.clear();
    }

    public List<Tourist> getTourists() {
        return tourists;
    }

    public List<Dinosaur> getDinosaurs() {
        return dinosaurs;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public ArrivalZone getArrivalZone() {
        return arrivalZone;
    }

    public CentralHub getCentralHub() {
        return centralHub;
    }

    public BathroomZone getBathroomZone() {
        return bathroomZone;
    }

    public PowerPlant getPowerPlant() {
        return powerPlant;
    }

    public List<ObservationEnclosure> getEnclosures() {
        return enclosures;
    }

    public Random getRng() {
        return rng;
    }

    public List<String> getActiveEventNames() {
        return activeEventNames;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public boolean isDealsHourActive() {
        return dealsHourActive;
    }

    public void setDealsHourActive(boolean dealsHourActive) {
        this.dealsHourActive = dealsHourActive;
    }

    public double getCurrentDiscount() {
        return currentDiscount;
    }

    public void setCurrentDiscount(double currentDiscount) {
        this.currentDiscount = currentDiscount;
    }
}