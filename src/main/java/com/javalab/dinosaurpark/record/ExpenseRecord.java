package com.javalab.dinosaurpark.record;

import java.time.LocalDateTime;

public record ExpenseRecord(
        String type,
        double amount,
        String description,
        LocalDateTime timestamp
) {}