package com.hydroyura.eta.telegram.infrastructure.bot.statemachine;

import com.hydroyura.eta.telegram.infrastructure.bot.command.CommandType;
import java.util.Set;

public enum State {

    UNREGISTERED(CommandType.START, CommandType.REGISTER, CommandType.HELP),
    ACTIVE(CommandType.START, CommandType.NEW_STUDENT, CommandType.START_LESSON, CommandType.HELP),
    IN_LESSON(CommandType.ADD_WORD, CommandType.END_LESSON, CommandType.HELP);

    private final Set<CommandType> allowedCommands;

    State(CommandType... allowedCommands) {
        this.allowedCommands = Set.of(allowedCommands);
    }

    public boolean allows(CommandType type) {
        return allowedCommands.contains(type);
    }
}
