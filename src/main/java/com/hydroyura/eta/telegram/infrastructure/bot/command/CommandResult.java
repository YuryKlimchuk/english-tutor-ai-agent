package com.hydroyura.eta.telegram.infrastructure.bot.command;

import java.util.Optional;

public record CommandResult(String text, Optional<Object> newContext) {

    public static CommandResult ok(String text) {
        return new CommandResult(text, Optional.empty());
    }

    public static CommandResult withContext(String text, Object newContext) {
        return new CommandResult(text, Optional.of(newContext));
    }
}
