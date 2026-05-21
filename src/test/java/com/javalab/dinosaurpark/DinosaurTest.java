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
    void testReturnToEnclosure() {
        Dinosaur d = new CarnivoreDinosaur(1, "Rex", "T-Rex");
        d.escape();
        d.returnToEnclosure();
        assertEquals(DinosaurStatus.RECAPTURED, d.getStatus());
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
        assertEquals("Carnivore", d.getDiet());
    }

    @Test
    void testHerbivoreDiet() {
        Dinosaur d = new HerbivoreDinosaur(2, "Dino", "Triceratops");
        assertEquals("Herbivore", d.getDiet());
    }
}