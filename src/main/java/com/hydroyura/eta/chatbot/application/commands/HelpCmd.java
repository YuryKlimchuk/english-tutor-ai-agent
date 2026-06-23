package com.hydroyura.eta.chatbot.application.commands;

import com.hydroyura.eta.chatbot.domain.statemachine.*;

public class HelpCmd implements Command {

    @Override public CommandType type() { return CommandType.HELP; }

    @Override
    public ExecutionResult execute(StateMachine sm) {
        var msg = switch (sm.getState()) {
            case UNREGISTERED -> "/register <name>";
            case ACTIVE -> "/newstudent <name> | /startlesson <name>";
            case IN_LESSON -> "/add <word> <POS> <tr> | /endlesson";
        };
        return new ExecutionResult(sm.getState(), sm.getContext(), msg);
    }
}
