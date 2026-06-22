package com.hydroyura.eta.telegram.domain.command;

import com.hydroyura.eta.telegram.domain.statemachine.Session;

public interface Command {

    /// @return text response + optional new session (null = no transition)
    Result run(Session session);

    record Result(String text, Session newSession) {
        public static Result stay(String text) { return new Result(text, null); }
        public static Result transition(String text, Session newSession) { return new Result(text, newSession); }
    }
}
