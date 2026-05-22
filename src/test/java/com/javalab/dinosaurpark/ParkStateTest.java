package com.javalab.dinosaurpark.simulation;

import com.javalab.dinosaurpark.model.*;
import com.javalab.dinosaurpark.zone.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ParkStateTest {

    private ParkState state;

    @BeforeEach
    void setUp() {
        ArrivalZone arrival      = new ArrivalZone("Entrada", 30, 25.0);
        CentralHub hub           = new CentralHub("Hub", 50, 15.0, 0.4);
        BathroomZone bathroom    = new BathroomZone("Baños", 10, 3, 20.0, 0.2);
        PowerPlant plant         = new PowerPlant(100.0, 1.5, 0.0);
        List<ObservationEnclosure> enclosures = new ArrayList<>();

        state = new ParkState(
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                arrival, hub, bathroom, plant, enclosures,
                new Random()
        );
    }

    // ── Estado inicial ────────────────────────────────────────────

    @Test
    void testInitialStepIsZero() {
        assertEquals(0, state.getCurrentStep());
    }

    @Test
    void testInitialRevenueIsZero() {
        assertEquals(0.0, state.getTotalRevenue(), 0.001);
    }

    @Test
    void testInitialExpensesIsZero() {
        assertEquals(0.0, state.getTotalExpenses(), 0.001);
    }

    @Test
    void testInitialDealsHourIsInactive() {
        assertFalse(state.isDealsHourActive());
    }

    @Test
    void testInitialDiscountIsZero() {
        assertEquals(0.0, state.getCurrentDiscount(), 0.001);
    }

    // ── Steps ─────────────────────────────────────────────────────

    @Test
    void testIncrementStep() {
        state.incrementStep();
        assertEquals(1, state.getCurrentStep());
    }

    // ── Turistas ──────────────────────────────────────────────────

    @Test
    void testCountActiveTouristsWhenEmpty() {
        assertEquals(0, state.countActiveTourists());
    }

    @Test
    void testCountActiveTouristsOnlyInPark() {
        Tourist t1 = new Tourist(1, "Ana");
        Tourist t2 = new Tourist(2, "Luis");
        t1.setStatus(TouristStatus.IN_PARK);

        state.getTourists().add(t1);
        state.getTourists().add(t2);

        assertEquals(1, state.countActiveTourists());
    }

    // ── Dinosaurios ───────────────────────────────────────────────

    @Test
    void testCountDinosaursInEnclosureWhenEmpty() {
        assertEquals(0, state.countDinosaursInEnclosure());
    }

    @Test
    void testCountDinosaursInEnclosureIgnoresEscaped() {
        Dinosaur d1 = new CarnivoreDinosaur(1, "Rex", "T-Rex");
        Dinosaur d2 = new CarnivoreDinosaur(2, "Blue", "Raptor");
        d2.escape();

        state.getDinosaurs().add(d1);
        state.getDinosaurs().add(d2);

        assertEquals(1, state.countDinosaursInEnclosure());
    }

    // ── Vehículos ─────────────────────────────────────────────────

    @Test
    void testCountVehiclesInUseWhenEmpty() {
        assertEquals(0, state.countVehiclesInUse());
    }

    @Test
    void testCountVehiclesInUse() {
        Vehicle v1 = new Vehicle(1, "Jeep", 3);
        Vehicle v2 = new Vehicle(2, "Bus", 3);
        v1.use();

        state.getVehicles().add(v1);
        state.getVehicles().add(v2);

        assertEquals(1, state.countVehiclesInUse());
    }

    // ── Ingresos y gastos ─────────────────────────────────────────

    @Test
    void testAddRevenue() {
        state.addRevenue(100.0);
        state.addRevenue(50.0);
        assertEquals(150.0, state.getTotalRevenue(), 0.001);
    }

    @Test
    void testAddExpense() {
        state.addExpense(200.0);
        assertEquals(200.0, state.getTotalExpenses(), 0.001);
    }

    // ── Eventos activos ───────────────────────────────────────────

    @Test
    void testAddActiveEvent() {
        state.addActiveEvent("BLACKOUT");
        assertEquals(1, state.getActiveEventNames().size());
        assertEquals("BLACKOUT", state.getActiveEventNames().get(0));
    }

    @Test
    void testClearActiveEventsResetsAll() {
        state.setDealsHourActive(true);
        state.setCurrentDiscount(0.30);
        state.addActiveEvent("HORA_DE_OFERTAS");

        state.clearActiveEvents();

        assertFalse(state.isDealsHourActive());
        assertEquals(0.0, state.getCurrentDiscount(), 0.001);
        assertTrue(state.getActiveEventNames().isEmpty());
    }

    // ── Descuento ─────────────────────────────────────────────────

    @Test
    void testSetAndGetDiscount() {
        state.setCurrentDiscount(0.30);
        assertEquals(0.30, state.getCurrentDiscount(), 0.001);
    }

    @Test
    void testSetDealsHourActive() {
        state.setDealsHourActive(true);
        assertTrue(state.isDealsHourActive());
    }
}