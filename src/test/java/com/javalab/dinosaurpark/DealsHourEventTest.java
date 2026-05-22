package com.javalab.dinosaurpark.event;

import com.javalab.dinosaurpark.config.ParkConfig;
import com.javalab.dinosaurpark.persistence.DatabaseService;
import com.javalab.dinosaurpark.record.EventRecord;
import com.javalab.dinosaurpark.simulation.ParkState;
import com.javalab.dinosaurpark.zone.*;
import com.javalab.dinosaurpark.model.PowerPlant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DealsHourEventTest {

    private ParkState state;
    private DealsHourEvent event;

    @BeforeEach
    void setup() throws Exception {

        state = new ParkState(
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                new ArrivalZone("Arrival", 10, 20),
                new CentralHub("Hub", 10, 10, 0.5),
                new BathroomZone("Bathroom", 10, 3, 10, 0.5),
                new PowerPlant(100, 1, 0.1),
                List.of(),
                new Random()
        );

        setFakeDb(state);

        event = new DealsHourEvent(0.8);
    }

    @Test
    void shouldActivateDealsHourAndDiscount() {

        event.execute(state, new Random());

        assertTrue(state.isDealsHourActive());
        assertEquals(0.30, state.getCurrentDiscount());
    }

    @Test
    void shouldExecuteWithoutErrors() {

        assertDoesNotThrow(() -> event.execute(state, new Random()));
    }

    private void setFakeDb(ParkState state) throws Exception {

        Field field = ParkState.class.getDeclaredField("db");
        field.setAccessible(true);

        field.set(state, new DatabaseService("mem:testdb"));
    }
}