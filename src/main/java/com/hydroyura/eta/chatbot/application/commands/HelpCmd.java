package com.hydroyura.eta.chatbot.application.commands;

import com.hydroyura.eta.chatbot.domain.command.Command;
import com.hydroyura.eta.chatbot.domain.command.Result;
import com.hydroyura.eta.chatbot.domain.statemachine.*;

public class HelpCmd implements Command {

    @Override public CommandType type() { return CommandType.HELP; }
    @Override public boolean matches(String text) { return text.equals("/help"); }

    @Override
    public Result execute(StateMachine sm, String userMessage) {
        var msg = switch (sm.getState()) {
            case NOT_REGISTER -> "/register <name>";
            case ACTIVE -> "/newstudent <name> | /startlesson <name>";
            case IN_LESSON -> "/add <word> <POS> <tr> | /endlesson";
        };
        return Result.stay(msg, type());
    }
}
