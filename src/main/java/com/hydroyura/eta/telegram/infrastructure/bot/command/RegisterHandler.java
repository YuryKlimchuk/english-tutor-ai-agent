package com.hydroyura.eta.telegram.infrastructure.bot.command;

import com.hydroyura.eta.telegram.infrastructure.bot.statemachine.StateContext;
import com.hydroyura.eta.teacher.api.teacher.FindTeacher;
import com.hydroyura.eta.teacher.api.teacher.RegisterTeacher;
import com.hydroyura.eta.teacher.api.teacher.RegisterTeacherCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterHandler implements BotCommand {

    private final RegisterTeacher registerTeacher;
    private final FindTeacher findTeacher;

    @Override public CommandType type() { return CommandType.REGISTER; }

    @Override
    public CommandResult execute(StateContext ctx, String text) {
        if (findTeacher.findByTelegramChatId(ctx.chatId()).isPresent()) {
            return CommandResult.ok("Already registered!");
        }
        var p = text.split(" ", 2);
        if (p.length < 2) return CommandResult.ok("Usage: /register <name>");
        registerTeacher.execute(new RegisterTeacherCommand(ctx.chatId(), p[1].trim()));
        return CommandResult.ok("✅ Registered! /newstudent <name>");
    }
}
