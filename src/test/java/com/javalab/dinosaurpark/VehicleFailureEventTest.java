package com.javalab.dinosaurpark.event;

import com.javalab.dinosaurpark.model.*;
import com.javalab.dinosaurpark.persistence.DatabaseService;
import com.javalab.dinosaurpark.record.EventRecord;
import com.javalab.dinosaurpark.simulation.ParkState;
import com.javalab.dinosaurpark.zone.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class VehicleFailureEventTest {

    private ParkState state;
    private VehicleFailureEvent event;

    @BeforeEach
    void setup() throws Exception {

        Vehicle v1 = new Vehicle(1, "Vehicle-1", 3);
        Vehicle v2 = new Vehicle(2, "Vehicle-2", 3);

        state = new ParkState(
                List.of(),
                List.of(),
                List.of(),
                List.of(v1, v2),
                new ArrivalZone("Arrival", 10, 20),
                new CentralHub("Hub", 10, 10, 0.5),
                new BathroomZone("Bathroom", 10, 3, 10, 0.5),
                new PowerPlant(100, 1, 0.1),
                List.of(),
                new Random()
        );

        setFakeDb(state);

        event = new VehicleFailureEvent(1.0);
    }

    @Test
    void shouldMarkVehicleAsBroken() {

        Random fixedRandom = new Random() {
            @Override
            public int nextInt(int bound) {
                return 0;
            }
        };

        Vehicle vehicle = state.getVehicles().get(0);

        event.execute(state, fixedRandom);

        assertEquals(VehicleStatus.BROKEN, vehicle.getStatus());
    }

    @Test
    void shouldNotCrashWhenNoVehiclesAvailable() {

        state.getVehicles().forEach(v -> v.markBroken());

        assertDoesNotThrow(() ->
                event.execute(state, new Random())
        );
    }

    @Test
    void shouldExecuteWithoutErrors() {

        assertDoesNotThrow(() ->
                event.execute(state, new Random())
        );
    }

    private void setFakeDb(ParkState state) throws Exception {

        Field field = ParkState.class.getDeclaredField("db");
        field.setAccessible(true);

        field.set(state, new DatabaseService("mem:testdb"));
    }
}