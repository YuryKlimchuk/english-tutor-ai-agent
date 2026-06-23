package com.hydroyura.eta.chatbot.application.commands;

import com.hydroyura.eta.chatbot.domain.statemachine.*;
import com.hydroyura.eta.student.api.lesson.EndLesson;
import com.hydroyura.eta.student.api.lesson.EndLessonCommand;

public class EndLessonCmd implements Command {

    private final EndLesson endLesson;

    public EndLessonCmd(EndLesson endLesson) {
        this.endLesson = endLesson;
    }

    @Override public CommandType type() { return CommandType.END_LESSON; }

    @Override
    public ExecutionResult execute(StateMachine sm) {
        if (!(sm.getContext() instanceof LessonContext lc))
            return new ExecutionResult(sm.getState(), sm.getContext(), "No active lesson");
        endLesson.execute(new EndLessonCommand(lc.getLessonId()));
        return new ExecutionResult(State.ACTIVE, new ActiveContext(lc.getTeacherId()),
            "✅ Lesson ended");
    }
}
