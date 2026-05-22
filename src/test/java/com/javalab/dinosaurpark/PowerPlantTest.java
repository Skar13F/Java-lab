package com.javalab.dinosaurpark.model;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PowerPlantTest {

    @Test
    void testInitiallyOperational() {
        PowerPlant plant = new PowerPlant(100.0, 1.5, 0.0);
        assertTrue(plant.isOperational());
    }

    @Test
    void testInitialEnergy() {
        PowerPlant plant = new PowerPlant(100.0, 1.5, 0.0);
        assertEquals(100.0, plant.getEnergy(), 0.001);
    }

    @Test
    void testTickReducesEnergy() {
        PowerPlant plant = new PowerPlant(100.0, 1.5, 0.0);
        plant.tick(new Random());
        assertEquals(98.5, plant.getEnergy(), 0.001);
    }

    @Test
    void testTickDoesNotConsumeEnergyWhenBroken() {
        PowerPlant plant = new PowerPlant(100.0, 1.5, 0.0);
        plant.triggerFailure();
        plant.tick(new Random());
        assertEquals(100.0, plant.getEnergy(), 0.001);
    }

    @Test
    void testTriggerFailureMakesPlantNotOperational() {
        PowerPlant plant = new PowerPlant(100.0, 1.5, 0.0);
        plant.triggerFailure();
        assertFalse(plant.isOperational());
    }

    @Test
    void testRepairRestoresOperational() {
        PowerPlant plant = new PowerPlant(100.0, 1.5, 0.0);
        plant.triggerFailure();
        plant.repair();
        assertTrue(plant.isOperational());
    }

    @Test
    void testTickWithProbabilityZeroNeverFails() {
        PowerPlant plant = new PowerPlant(100.0, 1.5, 0.0);
        for (int i = 0; i < 100; i++) {
            plant.tick(new Random());
        }
        assertTrue(plant.isOperational());
    }

    @Test
    void testTickWithProbabilityOneAlwaysFails() {
        PowerPlant plant = new PowerPlant(100.0, 1.5, 1.0);
        plant.tick(new Random());
        assertFalse(plant.isOperational());
    }
}