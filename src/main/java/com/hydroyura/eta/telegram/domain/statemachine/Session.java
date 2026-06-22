package com.hydroyura.eta.telegram.domain.statemachine;

import com.hydroyura.eta.student.api.lesson.LessonId;
import com.hydroyura.eta.student.api.student.StudentId;
import java.util.Set;

public sealed class Session
    permits Session.Unregistered, Session.Active, Session.Lesson {

    private final Long chatId;
    private final State state;

    Session(Long chatId, State state) { this.chatId = chatId; this.state = state; }

    public Long chatId() { return chatId; }
    public State state() { return state; }

    public static final class Unregistered extends Session {
        public Unregistered(Long chatId) { super(chatId, State.UNREGISTERED); }
    }

    public static final class Active extends Session {
        private final Set<StudentId> studentIds;
        public Active(Long chatId, Set<StudentId> studentIds) {
            super(chatId, State.ACTIVE); this.studentIds = studentIds;
        }
        public Set<StudentId> studentIds() { return studentIds; }
    }

    public static final class Lesson extends Session {
        private final StudentId studentId;
        private final LessonId lessonId;
        public Lesson(Long chatId, StudentId studentId, LessonId lessonId) {
            super(chatId, State.IN_LESSON); this.studentId = studentId; this.lessonId = lessonId;
        }
        public StudentId studentId() { return studentId; }
        public LessonId lessonId() { return lessonId; }
    }
}
