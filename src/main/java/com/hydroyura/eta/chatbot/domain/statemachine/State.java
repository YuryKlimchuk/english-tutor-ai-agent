package com.hydroyura.eta.chatbot.domain.statemachine;

import lombok.Getter;

import java.util.Set;

import static com.hydroyura.eta.chatbot.domain.statemachine.CommandType.*;

public enum State {

    UNREGISTERED(Context.class, START, REGISTER, HELP),
    ACTIVE(ActiveContext.class, START, NEW_STUDENT, START_LESSON, HELP),
    IN_LESSON(LessonContext.class, ADD_WORD, END_LESSON, HELP);

    private final Set<CommandType> allowedCommands;

    @Getter
    private final Class<? extends Context> contextClass;

    State(Class<? extends Context> contextClass, CommandType... commands) {
        this.allowedCommands = Set.of(commands);
        this.contextClass = contextClass;
    }

    public boolean allows(CommandType type) {
        return allowedCommands.contains(type);
    }
}
