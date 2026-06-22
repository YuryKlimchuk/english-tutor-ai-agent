package com.hydroyura.eta.exercise.api.exercise;

import java.util.Objects;
import java.util.UUID;

public record ExerciseId(UUID value) {

    public ExerciseId {
        Objects.requireNonNull(value, "ExerciseId must not be null");
    }

    public static ExerciseId generate() {
        return new ExerciseId(UUID.randomUUID());
    }
}
