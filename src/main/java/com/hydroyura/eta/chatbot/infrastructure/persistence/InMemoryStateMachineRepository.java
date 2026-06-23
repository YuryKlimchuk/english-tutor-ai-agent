package com.hydroyura.eta.chatbot.infrastructure.persistence;

import com.hydroyura.eta.chatbot.domain.statemachine.StateMachine;
import com.hydroyura.eta.chatbot.domain.statemachine.StateMachineRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryStateMachineRepository implements StateMachineRepository {

    private final Map<Long, StateMachine> store = new ConcurrentHashMap<>();

    @Override
    public Optional<StateMachine> findByChatId(Long chatId) {
        return Optional.ofNullable(store.get(chatId));
    }

    @Override
    public void save(StateMachine stateMachine) {
        store.put(stateMachine.getChatId(), stateMachine);
    }
}
