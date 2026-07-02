package com.hydroyura.eta.chatbot2.application;

import com.hydroyura.eta.chatbot2.domain.command.Command;
import com.hydroyura.eta.chatbot2.domain.command.CommandDispatcher;
import com.hydroyura.eta.chatbot2.domain.command.Result;
import com.hydroyura.eta.chatbot2.domain.statemachine.StateMachine;
import com.hydroyura.eta.chatbot2.domain.statemachine.StateMachineId;
import com.hydroyura.eta.chatbot2.domain.statemachine.StateMachineRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class StateMachineAppService {

    private final StateMachineRepository repository;
    private final CommandDispatcher dispatcher;

    public Result handle(String message, Long chatId) {
        var id = new StateMachineId(chatId);
        var sm = repository
                .findById(id)
                .orElse(StateMachine.ofDefaults(id));

        var result = getActualCommand(sm, message).execute(sm);
        repository.save(sm);
        return result;
    }

    private Command getActualCommand(StateMachine sm, String message) {
        return sm.getPendingCommandSafely()
                .map(dispatcher::get)
                .or(() -> Optional.ofNullable(dispatcher.dispatch(message)))
                // TODO: create custom exception
                .orElseThrow(RuntimeException::new);
    }

}
