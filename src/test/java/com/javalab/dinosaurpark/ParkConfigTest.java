package com.javalab.dinosaurpark.config;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ParkConfigTest {

    @BeforeEach
    void resetSingleton() {
        ParkConfig.resetForTesting();
    }

    @Test
    @DisplayName("getInstance() siempre retorna la misma instancia")
    void testSingletonSameInstance() {
        ParkConfig a = ParkConfig.getInstance();
        ParkConfig b = ParkConfig.getInstance();

        assertSame(a, b, "Debe ser exactamente el mismo objeto en memoria");
    }

    @Test
    @DisplayName("getInstance() no retorna null")
    void testInstanceNotNull() {
        assertNotNull(ParkConfig.getInstance());
    }

    @Test
    @DisplayName("Lee 'tourists' como entero desde park.properties")
    void testGetTotalTourists() {
        int tourists = ParkConfig.getInstance().getTotalTourists();

        assertTrue(tourists > 0, "Debe haber al menos 1 turista");
    }

    @Test
    @DisplayName("Lee 'simulation.totalSteps' correctamente")
    void testGetTotalSteps() {
        int steps = ParkConfig.getInstance().getTotalSteps();

        assertTrue(steps > 0, "Los steps deben ser positivos");
    }

    @Test
    @DisplayName("getDouble retorna defaultValue para clave inexistente")
    void testGetDoubleDefault() {
        double result = ParkConfig.getInstance()
                .getDouble("clave.inexistente", 99.9);

        assertEquals(99.9, result, 0.0001);
    }

    @Test
    @DisplayName("getInt retorna defaultValue para clave inexistente")
    void testGetIntDefault() {
        int result = ParkConfig.getInstance()
                .getInt("clave.inexistente", 42);

        assertEquals(42, result);
    }

    @Test
    @DisplayName("getString retorna defaultValue para clave inexistente")
    void testGetStringDefault() {
        String result = ParkConfig.getInstance()
                .getString("clave.inexistente", "default");

        assertEquals("default", result);
    }

    @Test
    @DisplayName("getInt obtiene valor existente")
    void testGetIntExistingValue() {
        ParkConfig cfg = ParkConfig.getInstance();

        int tourists = cfg.getInt("tourists", -1);

        assertNotEquals(-1, tourists);
        assertTrue(tourists > 0);
    }

    @Test
    @DisplayName("getDouble obtiene valor existente")
    void testGetDoubleExistingValue() {
        ParkConfig cfg = ParkConfig.getInstance();

        double salary = cfg.getDouble("workers.dailySalary", -1);

        assertNotEquals(-1, salary);
        assertTrue(salary > 0);
    }

    @Test
    @DisplayName("getString obtiene valor existente")
    void testGetStringExistingValue() {
        ParkConfig cfg = ParkConfig.getInstance();

        String output = cfg.getString("output.directory", "default");

        assertNotNull(output);
        assertFalse(output.isBlank());
    }

    @Test
    @DisplayName("Todas las probabilidades de eventos están entre 0.0 y 1.0")
    void testEventProbabilitiesInRange() {
        ParkConfig cfg = ParkConfig.getInstance();

        assertAll("probabilidades en rango [0.0, 1.0]",
                () -> assertTrue(inRange(cfg.getProbEscape()), "escape"),
                () -> assertTrue(inRange(cfg.getProbBlackout()), "blackout"),
                () -> assertTrue(inRange(cfg.getProbStorm()), "storm"),
                () -> assertTrue(inRange(cfg.getProbDealsHour()), "dealsHour"),
                () -> assertTrue(inRange(cfg.getProbVehicleFailure()), "vehicleFailure")
        );
    }

    @Test
    @DisplayName("Número de vehículos es positivo")
    void testVehicleCount() {
        assertTrue(ParkConfig.getInstance().getVehicleCount() > 0);
    }

    @Test
    @DisplayName("Steps de reparación de vehículo son positivos")
    void testVehicleRepairSteps() {
        assertTrue(ParkConfig.getInstance().getVehicleRepairSteps() > 0);
    }

    @Test
    @DisplayName("Todos los valores enteros son válidos")
    void testIntegerConfigurations() {
        ParkConfig cfg = ParkConfig.getInstance();

        assertAll(
                () -> assertTrue(cfg.getArrivalBatchSize() > 0),
                () -> assertTrue(cfg.getArrivalMaxCapacity() > 0),
                () -> assertTrue(cfg.getCarnivores() >= 0),
                () -> assertTrue(cfg.getHerbivores() >= 0),
                () -> assertTrue(cfg.getGuards() >= 0),
                () -> assertTrue(cfg.getTechnicians() >= 0),
                () -> assertTrue(cfg.getBathroomCapacity() > 0),
                () -> assertTrue(cfg.getBathroomDuration() > 0),
                () -> assertTrue(cfg.getBasicMaxVisitors() > 0),
                () -> assertTrue(cfg.getPremiumMaxVisitors() > 0),
                () -> assertTrue(cfg.getVipMaxVisitors() > 0),
                () -> assertTrue(cfg.getMonitoringInterval() > 0)
        );
    }

    @Test
    @DisplayName("Todos los valores double son válidos")
    void testDoubleConfigurations() {
        ParkConfig cfg = ParkConfig.getInstance();

        assertAll(
                () -> assertTrue(cfg.getDailySalary() >= 0),
                () -> assertTrue(cfg.getTicketPrice() >= 0),
                () -> assertTrue(cfg.getSouvenirPrice() >= 0),
                () -> assertTrue(cfg.getSouvenirProb() >= 0),
                () -> assertTrue(cfg.getSpaPrice() >= 0),
                () -> assertTrue(cfg.getSpaProb() >= 0),
                () -> assertTrue(cfg.getInitialEnergy() >= 0),
                () -> assertTrue(cfg.getEnergyConsumption() >= 0),
                () -> assertTrue(cfg.getFailureProb() >= 0),
                () -> assertTrue(cfg.getMaintenanceCost() >= 0),
                () -> assertTrue(cfg.getRepairCost() >= 0),
                () -> assertTrue(cfg.getBasicEntryFee() >= 0),
                () -> assertTrue(cfg.getPremiumEntryFee() >= 0),
                () -> assertTrue(cfg.getVipEntryFee() >= 0)
        );
    }

    @Test
    @DisplayName("Output directory no debe ser null ni vacío")
    void testOutputDirectory() {
        String outputDir = ParkConfig.getInstance().getOutputDirectory();

        assertNotNull(outputDir);
        assertFalse(outputDir.isBlank());
    }

    private boolean inRange(double value) {
        return value >= 0.0 && value <= 1.0;
    }
}