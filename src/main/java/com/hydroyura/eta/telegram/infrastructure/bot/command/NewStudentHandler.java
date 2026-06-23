package com.hydroyura.eta.telegram.infrastructure.bot.command;

import com.hydroyura.eta.telegram.infrastructure.bot.statemachine.StateContext;
import com.hydroyura.eta.teacher.api.teacher.CreateStudentWithDictionary;
import com.hydroyura.eta.teacher.api.teacher.CreateStudentWithDictionaryCommand;
import com.hydroyura.eta.teacher.api.teacher.FindTeacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewStudentHandler implements BotCommand {

    private final CreateStudentWithDictionary createStudentWithDictionary;
    private final FindTeacher findTeacher;

    @Override public CommandType type() { return CommandType.NEW_STUDENT; }

    @Override
    public CommandResult execute(StateContext ctx, String text) {
        var teacherId = findTeacher.findByTelegramChatId(ctx.chatId());
        if (teacherId.isEmpty()) return CommandResult.ok("Register first");
        var p = text.split(" ", 2);
        if (p.length < 2) return CommandResult.ok("Usage: /newstudent <name>");
        var name = p[1].trim();
        createStudentWithDictionary.execute(
            new CreateStudentWithDictionaryCommand(teacherId.get(), name, name + "'s Dictionary"));
        return CommandResult.ok("✅ Student " + name + " created");
    }
}
