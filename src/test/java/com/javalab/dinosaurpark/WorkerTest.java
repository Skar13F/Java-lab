package com.javalab.dinosaurpark.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {

    // ── Guard ────────────────────────────────────────────────────

    @Test
    void testGuardRole() {
        Guard g = new Guard(1, "Carlos", 150.0);
        assertEquals("GUARD", g.getRole());
    }

    @Test
    void testGuardGetters() {
        Guard g = new Guard(1, "Carlos", 150.0);
        assertEquals(1, g.getId());
        assertEquals("Carlos", g.getName());
        assertEquals(150.0, g.getDailySalary(), 0.001);
    }

    @Test
    void testGuardRecapturesEscapedDinosaur() {
        Guard g = new Guard(1, "Carlos", 150.0);
        Dinosaur dino = new CarnivoreDinosaur(1, "Rex", "T-Rex");
        dino.escape();

        g.recaptureEscapedDinosaurs(List.of(dino));

        assertEquals(DinosaurStatus.IN_ENCLOSURE, dino.getStatus());
    }

    @Test
    void testGuardIgnoresNonEscapedDinosaurs() {
        Guard g = new Guard(1, "Carlos", 150.0);
        Dinosaur dino = new CarnivoreDinosaur(1, "Rex", "T-Rex");

        g.recaptureEscapedDinosaurs(List.of(dino));

        assertEquals(DinosaurStatus.IN_ENCLOSURE, dino.getStatus());
    }

    @Test
    void testGuardRecapturesOnlyEscaped() {
        Guard g = new Guard(1, "Carlos", 150.0);
        Dinosaur escaped = new CarnivoreDinosaur(1, "Rex", "T-Rex");
        Dinosaur safe    = new HerbivoreDinosaur(2, "Dino", "Triceratops");
        escaped.escape();

        g.recaptureEscapedDinosaurs(List.of(escaped, safe));

        assertEquals(DinosaurStatus.IN_ENCLOSURE, escaped.getStatus());
        assertEquals(DinosaurStatus.IN_ENCLOSURE, safe.getStatus());
    }

    @Test
    void testGuardEmptyList() {
        Guard g = new Guard(1, "Carlos", 150.0);
        assertDoesNotThrow(() -> g.recaptureEscapedDinosaurs(new ArrayList<>()));
    }

    // ── Technician ───────────────────────────────────────────────

    @Test
    void testTechnicianRole() {
        Technician t = new Technician(2, "Ana", 200.0);
        assertEquals("TECHNICIAN", t.getRole());
    }

    @Test
    void testTechnicianRepairsPlantWhenBroken() {
        Technician t = new Technician(2, "Ana", 200.0);
        PowerPlant plant = new PowerPlant(100.0, 1.5, 0.0);
        plant.triggerFailure();

        t.repairIfNeeded(plant);

        assertTrue(plant.isOperational());
    }

    @Test
    void testTechnicianDoesNotTouchOperationalPlant() {
        Technician t = new Technician(2, "Ana", 200.0);
        PowerPlant plant = new PowerPlant(100.0, 1.5, 0.0);

        assertDoesNotThrow(() -> t.repairIfNeeded(plant));
        assertTrue(plant.isOperational());
    }

    @Test
    void testTechnicianRepairsWithAvailableVehicle() {
        Technician t = new Technician(2, "Ana", 200.0);
        PowerPlant plant = new PowerPlant(100.0, 1.5, 0.0);
        Vehicle vehicle = new Vehicle(1, "Jeep", 3);
        plant.triggerFailure();

        t.repairIfNeeded(plant, List.of(vehicle));

        assertTrue(plant.isOperational());
        assertEquals(VehicleStatus.AVAILABLE, vehicle.getStatus());
    }

    @Test
    void testTechnicianDoesNotRepairWithoutVehicle() {
        Technician t = new Technician(2, "Ana", 200.0);
        PowerPlant plant = new PowerPlant(100.0, 1.5, 0.0);
        Vehicle vehicle = new Vehicle(1, "Jeep", 3);
        vehicle.markBroken();
        plant.triggerFailure();

        t.repairIfNeeded(plant, List.of(vehicle));

        assertFalse(plant.isOperational());
    }

    @Test
    void testTechnicianEmptyVehicleList() {
        Technician t = new Technician(2, "Ana", 200.0);
        PowerPlant plant = new PowerPlant(100.0, 1.5, 0.0);
        plant.triggerFailure();

        assertDoesNotThrow(() -> t.repairIfNeeded(plant, new ArrayList<>()));
        assertFalse(plant.isOperational());
    }
}