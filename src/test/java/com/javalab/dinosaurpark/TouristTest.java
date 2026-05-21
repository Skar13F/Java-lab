package com.javalab.dinosaurpark.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TouristTest {

    @Test
    void testInitialStatus() {
        Tourist t = new Tourist(1, "Ana");
        assertEquals(TouristStatus.WAITING, t.getStatus());
    }

    @Test
    void testSpend() {
        Tourist t = new Tourist(1, "Ana");
        t.spend(50.0);
        t.spend(25.0);
        assertEquals(75.0, t.getMoneySpent(), 0.001);
    }

    @Test
    void testSetStatus() {
        Tourist t = new Tourist(1, "Ana");
        t.setStatus(TouristStatus.IN_PARK);
        assertEquals(TouristStatus.IN_PARK, t.getStatus());
    }

    @Test
    void testAttacked() {
        Tourist t = new Tourist(1, "Ana");
        t.setStatus(TouristStatus.ATTACKED);
        assertEquals(TouristStatus.ATTACKED, t.getStatus());
    }
}