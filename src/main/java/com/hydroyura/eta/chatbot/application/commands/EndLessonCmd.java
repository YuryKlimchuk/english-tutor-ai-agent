package com.hydroyura.eta.chatbot.application.commands;

import com.hydroyura.eta.chatbot.domain.command.Command;
import com.hydroyura.eta.chatbot.domain.command.Result;
import com.hydroyura.eta.chatbot.domain.statemachine.*;
import com.hydroyura.eta.student.api.lesson.EndLesson;
import com.hydroyura.eta.student.api.lesson.EndLessonCommand;
import com.hydroyura.eta.student.api.lesson.LessonId;
import java.util.UUID;

public class EndLessonCmd implements Command {

    private final EndLesson endLesson;

    public EndLessonCmd(EndLesson endLesson) { this.endLesson = endLesson; }

    @Override public CommandType type() { return CommandType.END_LESSON; }
    @Override public boolean matches(String text) { return text.equals("/endlesson"); }

    @Override
    public Result execute(StateMachine sm, String userMessage) {
        var lessonId = (UUID) sm.getContext().get("lessonId");
        if (lessonId == null) return Result.stay("No active lesson", type());
        endLesson.execute(new EndLessonCommand(new LessonId(lessonId)));
        return Result.transition("✅ Lesson ended", type(), State.ACTIVE, new Context());
    }
}
