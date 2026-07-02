package com.hydroyura.eta.chatbot.domain.command;

import com.hydroyura.eta.chatbot.domain.statemachine.CommandType;
import com.hydroyura.eta.chatbot.domain.statemachine.StateMachine;

public interface Command {

    CommandType type();

    Result execute(StateMachine sm, String userMessage);

    default boolean matches(String text) { return false; }
}
