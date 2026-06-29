package com.hydroyura.eta.chatbot.application;

import com.hydroyura.eta.chatbot.domain.statemachine.StateMachine;
import com.hydroyura.eta.chatbot.domain.statemachine.StateMachineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StateMachineService {

    private final StateMachineRepository repository;
    private final CommandDispatcher dispatcher;

    public String process(Long chatId, String text) {
        var sm = repository.findByChatId(chatId)
            .orElseGet(() -> new StateMachine(chatId));

        // If there's a pending command awaiting user input
        if (sm.getPendingCommand() != null && !text.startsWith("/")) {
            text = sm.getPendingCommand() + " " + text;
            sm.clearPendingCommand();
        }

        var command = dispatcher.dispatch(text);
        var response = sm.applyCommand(command);
        repository.save(sm);
        return response;
    }

    public com.hydroyura.eta.chatbot.domain.statemachine.State getState(Long chatId) {
        return repository.findByChatId(chatId)
            .map(StateMachine::getState)
            .orElse(com.hydroyura.eta.chatbot.domain.statemachine.State.UNREGISTERED);
    }
}
