package com.hydroyura.eta.student.api.student;

import com.hydroyura.eta.dictionary.api.dictionary.DictionaryId;
import java.util.Optional;

public interface StudentQuery {

    boolean existsByName(StudentExistsByNameQuery query);

    Optional<StudentId> findByNameIn(FindStudentByNameQuery query);

    Optional<DictionaryId> getDictionaryId(StudentId studentId);
}
