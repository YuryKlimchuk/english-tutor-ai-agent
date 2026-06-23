package com.hydroyura.eta.chatbot.domain.statemachine;

public record ExecutionResult(State state, Context context, String message) {
}
