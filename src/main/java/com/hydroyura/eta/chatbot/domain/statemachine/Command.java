package com.hydroyura.eta.chatbot.domain.statemachine;

public interface Command {

    CommandType type();

    ExecutionResult execute(StateMachine sm);
}
