package com.hydroyura.eta.telegram.infrastructure.bot.command;

import com.hydroyura.eta.student.api.lesson.EndLesson;
import com.hydroyura.eta.student.api.lesson.EndLessonCommand;
import com.hydroyura.eta.telegram.infrastructure.bot.statemachine.StateContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EndLessonHandler implements BotCommand {

    private final EndLesson endLesson;

    @Override public CommandType type() { return CommandType.END_LESSON; }

    @Override
    public CommandResult execute(StateContext ctx, String text) {
        if (!(ctx instanceof StateContext.Lesson lessonCtx)) {
            return CommandResult.ok("No active lesson");
        }
        endLesson.execute(new EndLessonCommand(lessonCtx.lessonId()));
        return CommandResult.ok("✅ Lesson ended");
    }
}
