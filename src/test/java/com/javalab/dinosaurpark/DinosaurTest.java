package com.javalab.dinosaurpark.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DinosaurTest {

    @Test
    void testInitialStatus() {
        Dinosaur d = new CarnivoreDinosaur(1, "Rex", "T-Rex");

        assertEquals(DinosaurStatus.IN_ENCLOSURE, d.getStatus());
    }

    @Test
    void testEscape() {
        Dinosaur d = new CarnivoreDinosaur(1, "Rex", "T-Rex");

        d.escape();

        assertEquals(DinosaurStatus.ESCAPED, d.getStatus());
    }

    @Test
    void testRecapture() {
        Dinosaur d = new CarnivoreDinosaur(1, "Rex", "T-Rex");

        d.escape();
        d.recapture();

        assertEquals(DinosaurStatus.RECAPTURED, d.getStatus());
    }

    @Test
    void testReturnToEnclosure() {
        Dinosaur d = new CarnivoreDinosaur(1, "Rex", "T-Rex");

        d.escape();
        d.returnToEnclosure();

        assertEquals(DinosaurStatus.IN_ENCLOSURE, d.getStatus());
    }

    @Test
    void testCarnivoreDangerLevel() {
        Dinosaur d = new CarnivoreDinosaur(1, "Rex", "T-Rex");

        assertEquals(0.9, d.getDangerLevel(), 0.001);
    }

    @Test
    void testHerbivoreDangerLevel() {
        Dinosaur d = new HerbivoreDinosaur(2, "Dino", "Triceratops");

        assertEquals(0.2, d.getDangerLevel(), 0.001);
    }

    @Test
    void testCarnivoreFeedingCost() {
        Dinosaur d = new CarnivoreDinosaur(1, "Rex", "T-Rex");

        assertEquals(500.0, d.getFeedingCostPerStep(), 0.001);
    }

    @Test
    void testHerbivoreFeedingCost() {
        Dinosaur d = new HerbivoreDinosaur(2, "Dino", "Triceratops");

        assertEquals(200.0, d.getFeedingCostPerStep(), 0.001);
    }

    @Test
    void testCarnivoreDiet() {
        Dinosaur d = new CarnivoreDinosaur(1, "Rex", "T-Rex");

        assertEquals("CARNIVORE", d.getDiet());
    }

    @Test
    void testHerbivoreDiet() {
        Dinosaur d = new HerbivoreDinosaur(2, "Dino", "Triceratops");

        assertEquals("HERBIVORE", d.getDiet());
    }

    @Test
    void testGetId() {
        Dinosaur d = new CarnivoreDinosaur(10, "Rex", "T-Rex");

        assertEquals(10, d.getId());
    }

    @Test
    void testGetName() {
        Dinosaur d = new CarnivoreDinosaur(1, "Rex", "T-Rex");

        assertEquals("Rex", d.getName());
    }

    @Test
    void testGetSpecies() {
        Dinosaur d = new HerbivoreDinosaur(2, "Dino", "Triceratops");

        assertEquals("Triceratops", d.getSpecies());
    }
}