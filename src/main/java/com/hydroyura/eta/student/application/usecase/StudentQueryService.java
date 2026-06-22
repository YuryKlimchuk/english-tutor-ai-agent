package com.hydroyura.eta.student.application.usecase;

import com.hydroyura.eta.student.api.student.StudentExistsByNameQuery;
import com.hydroyura.eta.student.api.student.StudentQuery;
import com.hydroyura.eta.student.domain.student.StudentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StudentQueryService implements StudentQuery {

    private final StudentRepository studentRepository;

    @Override
    public boolean existsByName(StudentExistsByNameQuery query) {
        return studentRepository.existsByNameInIds(query.studentIds(), query.name());
    }
}
