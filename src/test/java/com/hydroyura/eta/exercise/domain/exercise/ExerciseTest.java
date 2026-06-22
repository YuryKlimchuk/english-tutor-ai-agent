package com.hydroyura.eta.exercise.domain.exercise;

import com.hydroyura.eta.exercise.api.exercise.ExerciseId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExerciseTest {

    @Test
    void shouldCreateExercise() {
        var exercise = Exercise.create(ExerciseId.generate());
        assertThat(exercise.getId()).isNotNull();
    }
}
