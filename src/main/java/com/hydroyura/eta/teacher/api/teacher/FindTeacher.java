package com.hydroyura.eta.teacher.api.teacher;

import com.hydroyura.eta.student.api.student.StudentId;
import java.util.Optional;
import java.util.Set;

public interface FindTeacher {

    Optional<TeacherId> findByTelegramChatId(Long chatId);

    Set<StudentId> getStudentIds(Long chatId);
}
