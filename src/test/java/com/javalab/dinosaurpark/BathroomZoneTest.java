package com.javalab.dinosaurpark.zone;

import com.javalab.dinosaurpark.model.Tourist;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BathroomZoneTest {

    @Test
    void shouldAllowEntryWhenCapacityAvailable() {
        BathroomZone zone = new BathroomZone("Bathroom", 2, 3, 50, 0.0);

        Tourist t1 = new Tourist(1, "Juan");

        boolean result = zone.tryEnter(t1, new Random(1));

        assertTrue(result);
        assertEquals(1, zone.getCurrentOccupancy());
    }

    @Test
    void shouldRejectEntryWhenCapacityFull() {
        BathroomZone zone = new BathroomZone("Bathroom", 1, 3, 50, 0.0);

        Tourist t1 = new Tourist(1, "Juan");
        Tourist t2 = new Tourist(2, "Pedro");

        boolean r1 = zone.tryEnter(t1, new Random(1));
        boolean r2 = zone.tryEnter(t2, new Random(1));

        assertTrue(r1);
        assertFalse(r2);
        assertEquals(1, zone.getCurrentOccupancy());
    }

    @Test
    void shouldDecreaseUseDurationAndRemoveTouristOnTick() {
        BathroomZone zone = new BathroomZone("Bathroom", 2, 2, 50, 0.0);

        Tourist t1 = new Tourist(1, "Juan");

        zone.tryEnter(t1, new Random(1));

        assertEquals(1, zone.getCurrentOccupancy());

        zone.tick();
        assertEquals(1, zone.getCurrentOccupancy());

        zone.tick();
        assertEquals(0, zone.getCurrentOccupancy());
    }

    @Test
    void shouldAllowEntryWithoutCrashingEvenWithSpaProbability() {
        BathroomZone zone = new BathroomZone("Bathroom", 2, 3, 100, 1.0);

        Tourist t1 = new Tourist(1, "Juan");

        assertDoesNotThrow(() -> zone.tryEnter(t1, new Random(1)));

        assertEquals(1, zone.getCurrentOccupancy());
    }

    @Test
    void shouldAllowEntryWhenSpaProbabilityIsZero() {
        BathroomZone zone = new BathroomZone("Bathroom", 2, 3, 100, 0.0);

        Tourist t1 = new Tourist(1, "Juan");

        assertDoesNotThrow(() -> zone.tryEnter(t1, new Random(1)));

        assertEquals(1, zone.getCurrentOccupancy());
    }
}