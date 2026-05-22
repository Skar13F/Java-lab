package com.javalab.dinosaurpark.record;

import java.time.LocalDateTime;

public record EventRecord(
        int step,
        String eventName,
        String description,
        String affectedEntities,
        LocalDateTime timestamp
) {}