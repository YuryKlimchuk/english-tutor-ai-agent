package com.hydroyura.eta.teacher.application.usecase;

import com.hydroyura.eta.dictionary.api.dictionary.CreateDictionary;
import com.hydroyura.eta.dictionary.api.dictionary.CreateDictionaryCommand;
import com.hydroyura.eta.student.api.student.CreateStudent;
import com.hydroyura.eta.student.api.student.CreateStudentCommand;
import com.hydroyura.eta.student.api.student.StudentId;
import com.hydroyura.eta.teacher.api.teacher.CreateStudentWithDictionary;
import com.hydroyura.eta.teacher.api.teacher.CreateStudentWithDictionaryCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class CreateStudentWithDictionaryUseCase implements CreateStudentWithDictionary {

    private final CreateDictionary createDictionary;
    private final CreateStudent createStudent;

    @Override
    public StudentId execute(CreateStudentWithDictionaryCommand cmd) {
        var dictId = createDictionary.execute(new CreateDictionaryCommand(cmd.dictionaryName()));
        var studentId = createStudent.execute(new CreateStudentCommand(cmd.studentName(), dictId));

        log.info("Student '{}' created with dictionary '{}'", cmd.studentName(), cmd.dictionaryName());
        return studentId;
    }
}
