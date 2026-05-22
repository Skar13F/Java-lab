package com.javalab.dinosaurpark.zone;

import com.javalab.dinosaurpark.model.Tourist;
import com.javalab.dinosaurpark.model.TouristStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrivalZoneTest {

    private ArrivalZone zone;

    @BeforeEach
    void setUp() {
        zone = new ArrivalZone("Entrada", 10, 25.0);
    }

    @Test
    void testInitialOccupancyIsZero() {
        assertEquals(0, zone.getCurrentOccupancy());
    }

    @Test
    void testHasCapacityWhenEmpty() {
        assertTrue(zone.hasCapacity());
    }

    @Test
    void testEnterAddsTouristToQueue() {
        Tourist t = new Tourist(1, "Ana");
        zone.enter(t);
        assertEquals(1, zone.getCurrentOccupancy());
    }

    @Test
    void testEnterRespectsMaxCapacity() {
        for (int i = 1; i <= 10; i++) {
            zone.enter(new Tourist(i, "Tourist" + i));
        }
        zone.enter(new Tourist(11, "Extra"));
        assertEquals(10, zone.getCurrentOccupancy());
    }

    @Test
    void testProcessBatchChangesTouristStatusToInPark() {
        Tourist t = new Tourist(1, "Ana");
        zone.enter(t);
        zone.processBatch(5, 0.0);
        assertEquals(TouristStatus.IN_PARK, t.getStatus());
    }

    @Test
    void testProcessBatchChargesTicketPrice() {
        Tourist t = new Tourist(1, "Ana");
        zone.enter(t);
        zone.processBatch(5, 0.0);
        assertEquals(25.0, t.getMoneySpent(), 0.001);
    }

    @Test
    void testProcessBatchAppliesDiscount() {
        Tourist t = new Tourist(1, "Ana");
        zone.enter(t);
        zone.processBatch(5, 0.30);
        assertEquals(17.5, t.getMoneySpent(), 0.001);
    }

    @Test
    void testProcessBatchCreatesTicket() {
        Tourist t = new Tourist(1, "Ana");
        zone.enter(t);
        zone.processBatch(5, 0.0);
        assertEquals(1, zone.getSoldTickets().size());
    }

    @Test
    void testProcessBatchRespectsSize() {
        for (int i = 1; i <= 5; i++) {
            zone.enter(new Tourist(i, "Tourist" + i));
        }
        zone.processBatch(3, 0.0);
        assertEquals(3, zone.getSoldTickets().size());
    }

    @Test
    void testProcessBatchOnEmptyQueueDoesNothing() {
        assertDoesNotThrow(() -> zone.processBatch(5, 0.0));
        assertEquals(0, zone.getSoldTickets().size());
    }

    @Test
    void testExitRemovesTouristFromQueue() {
        Tourist t = new Tourist(1, "Ana");
        zone.enter(t);
        zone.exit(t);
        assertEquals(0, zone.getCurrentOccupancy());
    }

    @Test
    void testGetName() {
        assertEquals("Entrada", zone.getName());
    }

    @Test
    void testGetMaxCapacity() {
        assertEquals(10, zone.getMaxCapacity());
    }
}