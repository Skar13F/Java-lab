package com.javalab.dinosaurpark.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tourist {

    private final int id;
    private final String name;
    private TouristStatus status;
    private double moneySpent;
    private final List<String> visitedZones;

    public Tourist(int id, String name) {
        this.id = id;
        this.name = name;
        this.status = TouristStatus.WAITING;
        this.moneySpent = 0.0;
        this.visitedZones = new ArrayList<>();
    }

    public void spend(double amount) {
        this.moneySpent += amount;
    }

    public void recordVisit(String zone) {
        if (zone != null && !zone.isBlank()) {
            visitedZones.add(zone.trim());
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TouristStatus getStatus() {
        return status;
    }

    public double getMoneySpent() {
        return moneySpent;
    }

    public List<String> getVisitedZones() {
        return Collections.unmodifiableList(visitedZones);
    }

    public void setStatus(TouristStatus status) {
        this.status = status;
    }
}