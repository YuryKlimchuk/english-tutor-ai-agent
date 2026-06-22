package com.hydroyura.eta.exercise.domain.exercise;

import com.hydroyura.eta.exercise.api.exercise.ExerciseId;
import java.util.Optional;

public interface ExerciseRepository {

    Exercise save(Exercise exercise);

    Optional<Exercise> findById(ExerciseId id);
}
