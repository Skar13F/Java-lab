package com.javalab.dinosaurpark.zone;

import com.javalab.dinosaurpark.model.Tourist;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BathroomZone implements ParkZone {

    private final String name;
    private final int maxCapacity;
    private final int useDurationSteps;

    private final double spaPrice;
    private final double spaProbability;

    private final Map<Tourist, Integer> touristsUsing;

    public BathroomZone(String name,
                        int maxCapacity,
                        int useDurationSteps,
                        double spaPrice,
                        double spaProbability) {

        this.name = name;
        this.maxCapacity = maxCapacity;
        this.useDurationSteps = useDurationSteps;
        this.spaPrice = spaPrice;
        this.spaProbability = spaProbability;

        this.touristsUsing = new HashMap<>();
    }

    public boolean tryEnter(Tourist tourist, Random random) {

        if (!hasCapacity()) {
            return false;
        }

        touristsUsing.put(tourist, useDurationSteps);

        if (random.nextDouble() < spaProbability) {
            tourist.spend(spaPrice);
        }

        return true;
    }

    public void tick() {

        touristsUsing.replaceAll((t, steps) -> steps - 1);

        touristsUsing.entrySet()
                .removeIf(entry -> entry.getValue() <= 0);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasCapacity() {
        return touristsUsing.size() < maxCapacity;
    }

    @Override
    public int getCurrentOccupancy() {
        return touristsUsing.size();
    }

    @Override
    public int getMaxCapacity() {
        return maxCapacity;
    }

    @Override
    public void enter(Tourist tourist) {
        touristsUsing.put(tourist, useDurationSteps);
    }

    @Override
    public void exit(Tourist tourist) {
        touristsUsing.remove(tourist);
    }
}