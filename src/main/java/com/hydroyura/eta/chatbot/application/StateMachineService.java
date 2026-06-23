package com.hydroyura.eta.chatbot.application;

import com.hydroyura.eta.chatbot.domain.statemachine.StateMachine;
import com.hydroyura.eta.chatbot.domain.statemachine.StateMachineRepository;
import org.springframework.stereotype.Service;

@Service
public class StateMachineService {

    private final StateMachineRepository repository;
    private final CommandDispatcher dispatcher;

    public StateMachineService(StateMachineRepository repository, CommandDispatcher dispatcher) {
        this.repository = repository;
        this.dispatcher = dispatcher;
    }

    public String process(Long chatId, String text) {
        var sm = repository.findByChatId(chatId)
            .orElseGet(() -> new StateMachine(chatId));
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
