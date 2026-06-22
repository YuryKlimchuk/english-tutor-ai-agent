package com.hydroyura.eta.teacher.domain.teacher;

import com.hydroyura.eta.teacher.api.teacher.TeacherId;
import java.util.Objects;
import lombok.Getter;
import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Identity;

@Getter
@Entity
public class Teacher {

    @Identity
    private TeacherId id;
    private String name;

    private Teacher() {}

    public static Teacher create(TeacherId id, String name) {
        Objects.requireNonNull(id, "TeacherId must not be null");
        Objects.requireNonNull(name, "Name must not be null");

        var teacher = new Teacher();
        teacher.id = id;
        teacher.name = name;
        return teacher;
    }
}
