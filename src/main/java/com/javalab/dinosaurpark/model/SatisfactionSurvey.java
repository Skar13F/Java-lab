package com.javalab.dinosaurpark.model;

public class SatisfactionSurvey {

    private final int touristId;
    private final String enclosureName;
    private final int score;

    public SatisfactionSurvey(int touristId,
                              String enclosureName,
                              int score) {

        if (score < 1 || score > 5) {
            throw new IllegalArgumentException(
                    "Score debe estar entre 1 y 5"
            );
        }

        this.touristId = touristId;
        this.enclosureName = enclosureName;
        this.score = score;
    }

    public int getTouristId() {
        return touristId;
    }

    public String getEnclosureName() {
        return enclosureName;
    }

    public int getScore() {
        return score;
    }
}