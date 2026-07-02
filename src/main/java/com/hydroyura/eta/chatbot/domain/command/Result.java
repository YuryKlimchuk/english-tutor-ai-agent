package com.hydroyura.eta.chatbot.domain.command;

import com.hydroyura.eta.chatbot.domain.statemachine.CommandType;
import com.hydroyura.eta.chatbot.domain.statemachine.Context;

public record Result(String message, CommandType commandType, com.hydroyura.eta.chatbot.domain.statemachine.State state, java.util.Optional<Context> context) {

    public static Result stay(String message, CommandType type) {
        return new Result(message, type, null, java.util.Optional.empty());
    }

    public static Result transition(String message, CommandType type, com.hydroyura.eta.chatbot.domain.statemachine.State newState, Context newContext) {
        return new Result(message, type, newState, java.util.Optional.ofNullable(newContext));
    }
}
