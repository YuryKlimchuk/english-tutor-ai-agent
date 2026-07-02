package com.hydroyura.eta.chatbot.infrastructure.persistence;

import com.hydroyura.eta.chatbot.domain.statemachine.StateMachine;
import com.hydroyura.eta.chatbot.domain.statemachine.StateMachineId;
import com.hydroyura.eta.chatbot.domain.statemachine.StateMachineRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryStateMachineRepository implements StateMachineRepository {

    private final Map<StateMachineId, StateMachine> store = new ConcurrentHashMap<>();

    @Override
    public Optional<StateMachine> findById(StateMachineId id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void save(StateMachine stateMachine) {
        store.put(stateMachine.getId(), stateMachine);
    }
}
