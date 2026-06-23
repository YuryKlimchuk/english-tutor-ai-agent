package com.hydroyura.eta.telegram.infrastructure.bot.command;

import com.hydroyura.eta.student.api.lesson.LessonId;
import com.hydroyura.eta.student.api.lesson.StartLesson;
import com.hydroyura.eta.student.api.lesson.StartLessonCommand;
import com.hydroyura.eta.student.api.student.FindStudentByNameQuery;
import com.hydroyura.eta.student.api.student.StudentQuery;
import com.hydroyura.eta.telegram.infrastructure.bot.statemachine.StateContext;
import com.hydroyura.eta.teacher.api.teacher.FindTeacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartLessonHandler implements BotCommand {

    private final StartLesson startLesson;
    private final FindTeacher findTeacher;
    private final StudentQuery studentQuery;

    @Override public CommandType type() { return CommandType.START_LESSON; }

    @Override
    public CommandResult execute(StateContext ctx, String text) {
        var teacherId = findTeacher.findByTelegramChatId(ctx.chatId());
        if (teacherId.isEmpty()) return CommandResult.ok("Register first");
        var p = text.split(" ", 2);
        if (p.length < 2) return CommandResult.ok("Usage: /startlesson <student>");
        var name = p[1].trim();
        var ids = findTeacher.getStudentIds(ctx.chatId());
        var sid = studentQuery.findByNameIn(new FindStudentByNameQuery(ids, name));
        if (sid.isEmpty()) return CommandResult.ok("Student not found: " + name);
        var lid = startLesson.execute(new StartLessonCommand(sid.get(), "Lesson"));
        return CommandResult.withContext("✅ Lesson started: " + lid.value(),
            new StateContext.Lesson(ctx.chatId(), sid.get(), lid));
    }
}
