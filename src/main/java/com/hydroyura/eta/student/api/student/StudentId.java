package com.hydroyura.eta.student.api.student;

import java.util.Objects;
import java.util.UUID;

public record StudentId(UUID value) {

    public StudentId {
        Objects.requireNonNull(value, "StudentId must not be null");
    }

    public static StudentId generate() {
        return new StudentId(UUID.randomUUID());
    }
}
