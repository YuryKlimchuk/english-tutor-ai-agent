package com.hydroyura.eta.telegram.infrastructure.bot.statemachine;

import com.hydroyura.eta.telegram.infrastructure.bot.command.CommandDispatcher;
import com.hydroyura.eta.telegram.infrastructure.bot.command.CommandType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StateMachine {

    private final StateRepository stateRepository;
    private final CommandDispatcher dispatcher;

    public String process(Long chatId, String text) {
        var ctx = stateRepository.findByChatId(chatId)
            .orElseGet(() -> new StateContext.Unregistered(chatId));
        var command = dispatcher.dispatch(text);

        if (!ctx.state().allows(command.type())) {
            return "Command not available. Current state: " + ctx.state() + ". /help";
        }

        var result = command.execute(ctx, text);
        var newCtx = result.newContext()
            .map(c -> (StateContext) c)
            .orElse(ctx);
        stateRepository.save(newCtx);

        return result.text();
    }
}
