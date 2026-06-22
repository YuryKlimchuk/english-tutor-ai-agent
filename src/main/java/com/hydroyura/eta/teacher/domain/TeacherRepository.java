package com.hydroyura.eta.teacher.domain;

import com.hydroyura.eta.teacher.domain.model.Teacher;
import com.hydroyura.eta.teacher.api.TeacherId;
import java.util.Optional;

public interface TeacherRepository {

    Teacher save(Teacher teacher);

    Optional<Teacher> findById(TeacherId id);

    Optional<Teacher> findByTelegramId(String telegramId);
}
