package com.hydroyura.eta.chatbot.domain.statemachine;

import java.util.Optional;

public interface StateMachineRepository {

    Optional<StateMachine> findByChatId(Long chatId);

    void save(StateMachine stateMachine);
}
