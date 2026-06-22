package com.hydroyura.eta.student.domain.student;

import com.hydroyura.eta.student.api.lesson.LessonId;
import com.hydroyura.eta.student.api.student.StudentId;
import java.util.Optional;

public interface LessonRepository {

    Lesson save(Lesson lesson);

    Optional<Lesson> findById(LessonId id);

    Optional<Lesson> findActiveByStudentId(StudentId studentId);
}
