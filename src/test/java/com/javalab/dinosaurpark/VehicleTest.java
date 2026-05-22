package com.javalab.dinosaurpark.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    @Test
    void testInitialStatusIsAvailable() {
        Vehicle v = new Vehicle(1, "Jeep", 3);
        assertEquals(VehicleStatus.AVAILABLE, v.getStatus());
    }

    @Test
    void testUseChangesStatusToInUse() {
        Vehicle v = new Vehicle(1, "Jeep", 3);
        v.use();
        assertEquals(VehicleStatus.IN_USE, v.getStatus());
    }

    @Test
    void testFreeChangesStatusToAvailable() {
        Vehicle v = new Vehicle(1, "Jeep", 3);
        v.use();
        v.free();
        assertEquals(VehicleStatus.AVAILABLE, v.getStatus());
    }

    @Test
    void testMarkBrokenChangesStatusToBroken() {
        Vehicle v = new Vehicle(1, "Jeep", 3);
        v.markBroken();
        assertEquals(VehicleStatus.BROKEN, v.getStatus());
    }

    @Test
    void testMarkBrokenSetsRepairCountdown() {
        Vehicle v = new Vehicle(1, "Jeep", 3);
        v.markBroken();
        assertEquals(3, v.getRepairCountdown());
    }

    @Test
    void testTickReducesRepairCountdown() {
        Vehicle v = new Vehicle(1, "Jeep", 3);
        v.markBroken();
        v.tick();
        assertEquals(2, v.getRepairCountdown());
    }

    @Test
    void testVehicleReturnsToAvailableAfterRepair() {
        Vehicle v = new Vehicle(1, "Jeep", 3);
        v.markBroken();
        v.tick();
        v.tick();
        v.tick();
        assertEquals(VehicleStatus.AVAILABLE, v.getStatus());
    }

    @Test
    void testTickDoesNothingWhenAvailable() {
        Vehicle v = new Vehicle(1, "Jeep", 3);
        v.tick();
        assertEquals(VehicleStatus.AVAILABLE, v.getStatus());
    }
}