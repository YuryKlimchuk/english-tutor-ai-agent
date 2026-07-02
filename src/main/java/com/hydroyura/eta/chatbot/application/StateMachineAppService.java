package com.hydroyura.eta.chatbot.application;

import com.hydroyura.eta.chatbot.domain.command.CommandDispatcher;
import com.hydroyura.eta.chatbot.domain.statemachine.State;
import com.hydroyura.eta.chatbot.domain.statemachine.StateMachine;
import com.hydroyura.eta.chatbot.domain.statemachine.StateMachineId;
import com.hydroyura.eta.chatbot.domain.statemachine.StateMachineRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StateMachineAppService {

    private final StateMachineRepository repository;
    private final CommandDispatcher dispatcher;

    public String handle(String message, Long chatId) {
        var id = new StateMachineId(chatId);
        var sm = repository.findById(id).orElse(StateMachine.ofDefaults(id));

        // If pending command exists, use it; otherwise dispatch
        var command = sm.getPendingCommandSafely()
            .map(dispatcher::get)
            .orElseGet(() -> dispatcher.dispatch(message));

        if (command == null) {
            return "Unknown command. /help";
        }

        var response = sm.execute(command, message);
        sm.clearPendingCommand();
        repository.save(sm);
        return response;
    }

    public State getState(Long chatId) {
        return repository.findById(new StateMachineId(chatId))
            .map(StateMachine::getState)
            .orElse(State.NOT_REGISTER);
    }
}
