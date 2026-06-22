package com.hydroyura.eta.teacher.application.usecase;

import com.hydroyura.eta.teacher.api.teacher.RegisterTeacher;
import com.hydroyura.eta.teacher.api.teacher.RegisterTeacherCommand;
import com.hydroyura.eta.teacher.api.teacher.TeacherId;
import com.hydroyura.eta.teacher.domain.teacher.Teacher;
import com.hydroyura.eta.teacher.domain.teacher.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class RegisterTeacherUseCase implements RegisterTeacher {

    private final TeacherRepository teacherRepository;

    @Override
    public TeacherId execute(RegisterTeacherCommand cmd) {
        if (cmd.name() == null || cmd.name().isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }

        var teacher = Teacher.create(TeacherId.generate(), cmd.name());
        teacher = teacherRepository.save(teacher);

        log.info("Teacher '{}' registered: {}", cmd.name(), teacher.getId().value());
        return teacher.getId();
    }
}
