package com.javalab.dinosaurpark.zone;

import com.javalab.dinosaurpark.model.Tourist;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CentralHubTest {

    @Test
    void shouldEnterTouristWhenCapacityAllows() {
        CentralHub hub = new CentralHub("Hub", 2, 50, 0.0);

        Tourist t1 = new Tourist(1, "Juan");

        hub.enter(t1);

        assertEquals(1, hub.getCurrentOccupancy());
    }

    @Test
    void shouldNotEnterWhenCapacityIsFull() {
        CentralHub hub = new CentralHub("Hub", 1, 50, 0.0);

        Tourist t1 = new Tourist(1, "Juan");
        Tourist t2 = new Tourist(2, "Pedro");

        hub.enter(t1);
        hub.enter(t2);

        assertEquals(1, hub.getCurrentOccupancy());
    }

    @Test
    void shouldReduceMoneyWhenSouvenirIsBought() {
        CentralHub hub = new CentralHub("Hub", 2, 100, 1.0);

        Tourist t1 = new Tourist(1, "Juan");
        double initialSpent = t1.getMoneySpent();

        hub.visit(t1, new Random(), 0.0);

        assertTrue(t1.getMoneySpent() > initialSpent,
                "El gasto debería aumentar tras comprar souvenir");
    }

    @Test
    void shouldApplyDiscountCorrectly() {
        CentralHub hub = new CentralHub("Hub", 2, 100, 1.0);

        Tourist t1 = new Tourist(1, "Juan");

        // 30% descuento → paga 70
        hub.visit(t1, new Random(), 0.3);

        assertEquals(70.0, t1.getMoneySpent(), 0.001);
    }

    @Test
    void shouldNotChargeWhenProbabilityIsZero() {
        CentralHub hub = new CentralHub("Hub", 2, 100, 0.0);

        Tourist t1 = new Tourist(1, "Juan");
        double initialSpent = t1.getMoneySpent();

        hub.visit(t1, new Random(), 0.5);

        assertEquals(initialSpent, t1.getMoneySpent(), 0.001);
    }

    @Test
    void shouldRemoveTouristOnExit() {
        CentralHub hub = new CentralHub("Hub", 2, 50, 0.0);

        Tourist t1 = new Tourist(1, "Juan");

        hub.enter(t1);
        assertEquals(1, hub.getCurrentOccupancy());

        hub.exit(t1);
        assertEquals(0, hub.getCurrentOccupancy());
    }
}