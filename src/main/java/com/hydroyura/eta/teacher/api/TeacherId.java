package com.hydroyura.eta.teacher.api;

import java.util.Objects;
import java.util.UUID;

public record TeacherId(UUID value) {

    public TeacherId {
        Objects.requireNonNull(value, "TeacherId must not be null");
    }

    public static TeacherId generate() {
        return new TeacherId(UUID.randomUUID());
    }
}
