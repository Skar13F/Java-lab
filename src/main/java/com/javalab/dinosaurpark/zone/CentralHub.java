package com.javalab.dinosaurpark.zone;

import com.javalab.dinosaurpark.model.Tourist;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CentralHub implements ParkZone {

    private final String name;
    private final int maxCapacity;
    private final double souvenirPrice;
    private final double souvenirProbability;

    private final List<Tourist> tourists;

    public CentralHub(String name,
                      int maxCapacity,
                      double souvenirPrice,
                      double souvenirProbability) {

        this.name = name;
        this.maxCapacity = maxCapacity;
        this.souvenirPrice = souvenirPrice;
        this.souvenirProbability = souvenirProbability;

        this.tourists = new ArrayList<>();
    }

    public void visit(Tourist tourist, Random random, double discount) {

        enter(tourist);

        if (random.nextDouble() < souvenirProbability) {

            double finalPrice = souvenirPrice * (1 - discount);

            tourist.spend(finalPrice);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasCapacity() {
        return tourists.size() < maxCapacity;
    }

    @Override
    public int getCurrentOccupancy() {
        return tourists.size();
    }

    @Override
    public int getMaxCapacity() {
        return maxCapacity;
    }

    @Override
    public void enter(Tourist tourist) {
        if (hasCapacity()) {
            tourists.add(tourist);
        }
    }

    @Override
    public void exit(Tourist tourist) {
        tourists.remove(tourist);
    }
}