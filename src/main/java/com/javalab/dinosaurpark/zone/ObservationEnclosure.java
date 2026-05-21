package com.javalab.dinosaurpark.zone;

import com.javalab.dinosaurpark.model.Dinosaur;
import com.javalab.dinosaurpark.model.SatisfactionSurvey;
import com.javalab.dinosaurpark.model.Tourist;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ObservationEnclosure implements ParkZone {

    private final String name;
    private final int maxCapacity;
    private final double entryFee;
    private final ExperienceType type;

    private final List<Tourist> tourists;
    private final List<Dinosaur> dinosaurs;

    public ObservationEnclosure(String name,
                                int maxCapacity,
                                double entryFee,
                                ExperienceType type,
                                List<Dinosaur> dinosaurs) {

        this.name = name;
        this.maxCapacity = maxCapacity;
        this.entryFee = entryFee;
        this.type = type;
        this.dinosaurs = dinosaurs;

        this.tourists = new ArrayList<>();
    }

    public void visit(Tourist tourist,
                      Random random) {

        if (!hasCapacity()) {
            return;
        }

        enter(tourist);

        tourist.spend(entryFee);

        conductSurvey(tourist, random);
    }

    public SatisfactionSurvey conductSurvey(Tourist tourist,
                                            Random random) {

        int min;
        int max;

        switch (type) {

            case BASIC -> {
                min = 1;
                max = 3;
            }

            case PREMIUM -> {
                min = 2;
                max = 4;
            }

            case VIP -> {
                min = 3;
                max = 5;
            }

            default -> throw new IllegalStateException("Tipo inválido");
        }

        int score = random.nextInt(max - min + 1) + min;

        return new SatisfactionSurvey(
                tourist.getId(),
                name,
                score
        );
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
        tourists.add(tourist);
    }

    @Override
    public void exit(Tourist tourist) {
        tourists.remove(tourist);
    }

    public List<Dinosaur> getDinosaurs() {
        return dinosaurs;
    }

    public ExperienceType getType() {
        return type;
    }
}