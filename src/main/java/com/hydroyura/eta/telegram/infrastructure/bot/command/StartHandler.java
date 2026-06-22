package com.hydroyura.eta.telegram.infrastructure.bot.command;

import com.hydroyura.eta.telegram.infrastructure.bot.statemachine.StateContext;
import com.hydroyura.eta.teacher.api.teacher.FindTeacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartHandler implements BotCommand {

    private final FindTeacher findTeacher;

    @Override public CommandType type() { return CommandType.START; }

    @Override
    public CommandResult execute(StateContext ctx, String text) {
        var teacherId = findTeacher.findByTelegramChatId(ctx.chatId());
        if (teacherId.isPresent()) {
            return CommandResult.ok("Welcome back! /newstudent <name>");
        }
        return CommandResult.ok("Welcome! /register <name>");
    }
}
