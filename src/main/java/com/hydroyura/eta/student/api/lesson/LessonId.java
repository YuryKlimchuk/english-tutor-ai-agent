package com.hydroyura.eta.student.api.lesson;

import java.util.Objects;
import java.util.UUID;

public record LessonId(UUID value) {

    public LessonId {
        Objects.requireNonNull(value, "LessonId must not be null");
    }

    public static LessonId generate() {
        return new LessonId(UUID.randomUUID());
    }
}
