package com.javalab.dinosaurpark.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ParkConfig {

    private static volatile ParkConfig instance;
    private final Properties props;
    private static final String CONFIG_FILE = "park.properties";

    private ParkConfig() {
        props = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (is == null) {
                throw new IllegalStateException("No se encontró '" + CONFIG_FILE + "' en src/main/resources/");
            }
            props.load(is);
        } catch (IOException e) {
            throw new IllegalStateException("Error al leer " + CONFIG_FILE, e);
        }
    }

    public static ParkConfig getInstance() {
        if (instance == null) {
            synchronized (ParkConfig.class) {
                if (instance == null) {
                    instance = new ParkConfig();
                }
            }
        }
        return instance;
    }

    public int getInt(String key, int defaultValue) {
        String value = props.getProperty(key);
        if (value == null) return defaultValue;
        try { return Integer.parseInt(value.trim()); }
        catch (NumberFormatException e) { return defaultValue; }
    }

    public double getDouble(String key, double defaultValue) {
        String value = props.getProperty(key);
        if (value == null) return defaultValue;
        try { return Double.parseDouble(value.trim()); }
        catch (NumberFormatException e) { return defaultValue; }
    }

    public String getString(String key, String defaultValue) {
        return props.getProperty(key, defaultValue).trim();
    }

    public int getTotalSteps()          { return getInt("simulation.totalSteps", 100); }
    public int getArrivalBatchSize()    { return getInt("simulation.arrivalBatchSize", 5); }
    public int getTotalTourists()       { return getInt("tourists", 50); }
    public int getCarnivores()          { return getInt("dinosaurs.carnivores", 5); }
    public int getHerbivores()          { return getInt("dinosaurs.herbivores", 15); }
    public int getGuards()              { return getInt("workers.guards", 3); }
    public int getTechnicians()         { return getInt("workers.technicians", 2); }
    public double getDailySalary()      { return getDouble("workers.dailySalary", 150.0); }
    public int getArrivalMaxCapacity()  { return getInt("arrival.maxCapacity", 30); }
    public double getTicketPrice()      { return getDouble("arrival.ticketPrice", 25.0); }
    public double getSouvenirPrice()    { return getDouble("hub.souvenirPrice", 15.0); }
    public double getSouvenirProb()     { return getDouble("hub.souvenirPurchaseProbability", 0.4); }
    public int getBathroomCapacity()    { return getInt("bathroom.maxCapacity", 10); }
    public int getBathroomDuration()    { return getInt("bathroom.useDurationSteps", 3); }
    public double getSpaPrice()         { return getDouble("bathroom.spaPrice", 20.0); }
    public double getSpaProb()          { return getDouble("bathroom.spaPurchaseProbability", 0.2); }
    public double getInitialEnergy()    { return getDouble("powerplant.initialEnergy", 100.0); }
    public double getEnergyConsumption(){ return getDouble("powerplant.consumptionPerStep", 1.5); }
    public double getFailureProb()      { return getDouble("powerplant.failureProbability", 0.05); }
    public double getMaintenanceCost()  { return getDouble("powerplant.maintenanceCost", 200.0); }
    public double getRepairCost()       { return getDouble("powerplant.repairCost", 500.0); }
    public int getBasicMaxVisitors()    { return getInt("enclosure.basic.maxVisitors", 20); }
    public double getBasicEntryFee()    { return getDouble("enclosure.basic.entryFee", 10.0); }
    public int getPremiumMaxVisitors()  { return getInt("enclosure.premium.maxVisitors", 12); }
    public double getPremiumEntryFee()  { return getDouble("enclosure.premium.entryFee", 30.0); }
    public int getVipMaxVisitors()      { return getInt("enclosure.vip.maxVisitors", 5); }
    public double getVipEntryFee()      { return getDouble("enclosure.vip.entryFee", 75.0); }
    public int getVehicleCount()        { return getInt("vehicles.count", 5); }
    public int getVehicleRepairSteps()  { return getInt("vehicles.repairSteps", 3); }
    public double getProbEscape()       { return getDouble("event.prob.escape", 0.05); }
    public double getProbBlackout()     { return getDouble("event.prob.blackout", 0.03); }
    public double getProbStorm()        { return getDouble("event.prob.storm", 0.04); }
    public double getProbDealsHour()    { return getDouble("event.prob.dealsHour", 0.08); }
    public double getProbVehicleFailure(){ return getDouble("event.prob.vehicleFailure", 0.06); }
    public int getMonitoringInterval()  { return getInt("monitoring.intervalSteps", 10); }
    public String getOutputDirectory()  { return getString("output.directory", "output"); }

    static void resetForTesting() {
        synchronized (ParkConfig.class) {
            instance = null;
        }
    }
}