package com.hydroyura.eta.chatbot.domain.statemachine;

import com.hydroyura.eta.student.api.lesson.LessonId;
import com.hydroyura.eta.student.api.student.StudentId;
import com.hydroyura.eta.teacher.api.teacher.TeacherId;
import lombok.Getter;

@Getter
public final class LessonContext extends ActiveContext {

    private final StudentId studentId;
    private final LessonId lessonId;

    public LessonContext(TeacherId id, StudentId studentId, LessonId lessonId) {
        super(id);
        this.studentId = studentId;
        this.lessonId = lessonId;
    }

}
