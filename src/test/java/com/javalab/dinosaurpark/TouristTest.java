package com.javalab.dinosaurpark.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TouristTest {

    @Test
    void testInitialStatus() {
        Tourist t = new Tourist(1, "Ana");

        assertEquals(TouristStatus.WAITING, t.getStatus());
    }

    @Test
    void testConstructorValues() {
        Tourist t = new Tourist(1, "Ana");

        assertEquals(1, t.getId());
        assertEquals("Ana", t.getName());
        assertEquals(0.0, t.getMoneySpent(), 0.001);
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

    @Test
    void testRecordVisit() {
        Tourist t = new Tourist(1, "Ana");

        t.recordVisit("Zona Carnívoros");

        assertEquals(1, t.getVisitedZones().size());
        assertEquals("Zona Carnívoros", t.getVisitedZones().get(0));
    }

    @Test
    void testVisitedZonesInitiallyEmpty() {
        Tourist t = new Tourist(1, "Ana");

        assertTrue(t.getVisitedZones().isEmpty());
    }

    @Test
    void testMultipleVisits() {
        Tourist t = new Tourist(1, "Ana");

        t.recordVisit("Zona A");
        t.recordVisit("Zona B");

        List<String> zones = t.getVisitedZones();

        assertEquals(2, zones.size());
    }
}