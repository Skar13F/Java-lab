package com.javalab.dinosaurpark.model;

public class Tourist {

    private final int id;
    private final String name;
    private TouristStatus status;
    private double moneySpent;

    public Tourist(int id, String name) {
        this.id = id;
        this.name = name;
        this.status = TouristStatus.WAITING;
        this.moneySpent = 0.0;
    }

    public void spend(double amount) {
        this.moneySpent += amount;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public TouristStatus getStatus() { return status; }
    public double getMoneySpent() { return moneySpent; }

    public void setStatus(TouristStatus status) { this.status = status; }
}