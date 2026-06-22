package com.hydroyura.eta.exercise.domain.model;

import com.hydroyura.eta.exercise.api.ExerciseId;
import java.util.Objects;
import lombok.Getter;
import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Identity;

@Getter
@Entity
public class Exercise {

    @Identity
    private ExerciseId id;

    private Exercise() {}

    public static Exercise create(ExerciseId id) {
        Objects.requireNonNull(id, "ExerciseId must not be null");

        var exercise = new Exercise();
        exercise.id = id;
        return exercise;
    }
}
