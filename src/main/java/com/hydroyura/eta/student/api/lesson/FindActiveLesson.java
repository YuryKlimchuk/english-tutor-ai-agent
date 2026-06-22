package com.hydroyura.eta.student.api.lesson;

import com.hydroyura.eta.student.api.student.StudentId;
import java.util.Optional;

public interface FindActiveLesson {

    Optional<LessonId> findByStudentId(StudentId studentId);
}
