package com.hydroyura.eta.telegram.infrastructure.bot.command;

import com.hydroyura.eta.telegram.infrastructure.bot.statemachine.StateContext;

public interface BotCommand {

    CommandType type();

    CommandResult execute(StateContext context, String text);
}
