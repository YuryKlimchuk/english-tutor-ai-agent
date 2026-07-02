package com.hydroyura.eta.chatbot2.domain.statemachine;

import java.util.Optional;

public interface StateMachineRepository {

    Optional<StateMachine> findById(StateMachineId id);

    void save(StateMachine stateMachine);

}
