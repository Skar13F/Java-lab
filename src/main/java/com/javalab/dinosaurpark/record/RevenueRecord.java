package com.javalab.dinosaurpark.record;

import java.time.LocalDateTime;

public record RevenueRecord(
        String type,
        double amount,
        int touristId,
        String zone,
        LocalDateTime timestamp
) {}