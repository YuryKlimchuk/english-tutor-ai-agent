package com.hydroyura.eta.student.api.student;

import java.util.Optional;

public interface StudentQuery {

    boolean existsByName(StudentExistsByNameQuery query);

    Optional<StudentId> findByNameIn(FindStudentByNameQuery query);
}
