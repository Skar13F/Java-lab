package com.javalab.dinosaurpark.persistence;

import com.javalab.dinosaurpark.record.EventRecord;
import com.javalab.dinosaurpark.record.ExpenseRecord;
import com.javalab.dinosaurpark.record.RevenueRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseServiceTest {

    private DatabaseService service;

    @BeforeEach
    void setup() {
        service = new DatabaseService("mem:testdb;DB_CLOSE_DELAY=-1");
    }

    @AfterEach
    void tearDown() {
        service.close();
    }

    @Test
    void shouldCreateServiceWithoutErrors() {
        assertNotNull(service);
        assertNotNull(service.getConnection());
    }

    @Test
    void shouldInsertRevenueWithoutException() {
        RevenueRecord record = new RevenueRecord(
                "TICKET",
                100.0,
                1,
                "ARRIVAL",
                LocalDateTime.now()
        );

        assertDoesNotThrow(() -> service.appendRevenue(record));
    }

    @Test
    void shouldInsertExpenseWithoutException() {
        ExpenseRecord record = new ExpenseRecord(
                "SALARY",
                200.0,
                "Worker payment",
                LocalDateTime.now()
        );

        assertDoesNotThrow(() -> service.appendExpense(record));
    }

    @Test
    void shouldInsertEventWithoutException() {
        EventRecord record = new EventRecord(
                1,
                "VEHICLE_FAILURE",
                "Broken vehicle",
                "Vehicle-1",
                LocalDateTime.now()
        );

        assertDoesNotThrow(() -> service.appendEvent(record));
    }

    @Test
    void shouldCloseConnectionWithoutErrors() {
        assertDoesNotThrow(() -> service.close());
    }
}