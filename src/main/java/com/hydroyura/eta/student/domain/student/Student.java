package com.hydroyura.eta.student.domain.student;

import com.hydroyura.eta.dictionary.api.dictionary.DictionaryId;
import com.hydroyura.eta.student.api.student.StudentId;
import java.util.Objects;
import lombok.Getter;
import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Identity;

@Getter
@Entity
public class Student {

    @Identity
    private StudentId id;
    private DictionaryId dictionaryId;
    private String name;

    private Student() {}

    public static Student create(StudentId id, DictionaryId dictionaryId, String name) {
        Objects.requireNonNull(id, "StudentId must not be null");
        Objects.requireNonNull(dictionaryId, "DictionaryId must not be null");
        Objects.requireNonNull(name, "Name must not be null");

        var student = new Student();
        student.id = id;
        student.dictionaryId = dictionaryId;
        student.name = name;
        return student;
    }
}
