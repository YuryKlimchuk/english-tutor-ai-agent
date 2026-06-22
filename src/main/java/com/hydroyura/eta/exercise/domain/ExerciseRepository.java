package com.hydroyura.eta.exercise.domain;

import com.hydroyura.eta.exercise.api.ExerciseId;
import com.hydroyura.eta.exercise.domain.model.Exercise;
import java.util.Optional;

public interface ExerciseRepository {

    Exercise save(Exercise exercise);

    Optional<Exercise> findById(ExerciseId id);
}
