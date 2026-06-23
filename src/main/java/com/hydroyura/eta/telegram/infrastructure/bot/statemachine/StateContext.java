package com.hydroyura.eta.telegram.infrastructure.bot.statemachine;

import com.hydroyura.eta.student.api.lesson.LessonId;
import com.hydroyura.eta.student.api.student.StudentId;

import java.util.Set;

public sealed class StateContext
    permits StateContext.Unregistered, StateContext.Active, StateContext.Lesson {

    private final Long chatId;
    private final State state;

    public StateContext(Long chatId, State state) {
        this.chatId = chatId;
        this.state = state;
    }

    public Long chatId() { return chatId; }
    public State state() { return state; }

    // --- subclasses ---

    public static final class Unregistered extends StateContext {
        public Unregistered(Long chatId) { super(chatId, com.hydroyura.eta.telegram.infrastructure.bot.statemachine.State.UNREGISTERED); }
    }

    public static final class Active extends StateContext {
        private final Set<StudentId> studentIds;

        public Active(Long chatId, Set<StudentId> studentIds) {
            super(chatId, com.hydroyura.eta.telegram.infrastructure.bot.statemachine.State.ACTIVE);
            this.studentIds = studentIds;
        }

        public Set<StudentId> studentIds() { return studentIds; }
    }

    public static final class Lesson extends StateContext {
        private final StudentId studentId;
        private final LessonId lessonId;

        public Lesson(Long chatId, StudentId studentId, LessonId lessonId) {
            super(chatId, com.hydroyura.eta.telegram.infrastructure.bot.statemachine.State.IN_LESSON);
            this.studentId = studentId;
            this.lessonId = lessonId;
        }

        public StudentId studentId() { return studentId; }
        public LessonId lessonId() { return lessonId; }
    }
}
