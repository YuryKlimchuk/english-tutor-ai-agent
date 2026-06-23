package com.hydroyura.eta.telegram.infrastructure.bot.command;

import com.hydroyura.eta.telegram.infrastructure.bot.statemachine.StateContext;
import org.springframework.stereotype.Component;

@Component
public class HelpHandler implements BotCommand {

    @Override public CommandType type() { return CommandType.HELP; }

    @Override
    public CommandResult execute(StateContext ctx, String text) {
        return CommandResult.ok("/register <name> | /newstudent <name> | /startlesson <name> | /add <word> <POS> <tr> | /endlesson");
    }
}
