package com.hydroyura.eta.student.application.usecase;

import com.hydroyura.eta.dictionary.api.dictionary.DictionaryId;
import com.hydroyura.eta.student.api.student.FindStudentByNameQuery;
import com.hydroyura.eta.student.api.student.StudentExistsByNameQuery;
import com.hydroyura.eta.student.api.student.StudentId;
import com.hydroyura.eta.student.api.student.StudentQuery;
import com.hydroyura.eta.student.domain.student.StudentRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class StudentQueryService implements StudentQuery {

    private final StudentRepository studentRepository;

    @Override
    public boolean existsByName(StudentExistsByNameQuery query) {
        return studentRepository.existsByNameInIds(query.studentIds(), query.name());
    }

    @Override
    public Optional<StudentId> findByNameIn(FindStudentByNameQuery query) {
        return query.studentIds().stream()
            .map(studentRepository::findById)
            .flatMap(Optional::stream)
            .filter(s -> s.getName().equalsIgnoreCase(query.name()))
            .map(s -> s.getId())
            .findFirst();
    }

    @Override
    public Optional<DictionaryId> getDictionaryId(StudentId studentId) {
        return studentRepository.findById(studentId)
            .map(s -> s.getDictionaryId());
    }
}
