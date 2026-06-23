package com.hydroyura.eta.telegram.infrastructure.bot.statemachine;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryStateRepository implements StateRepository {

    private final Map<Long, StateContext> store = new ConcurrentHashMap<>();

    @Override
    public Optional<StateContext> findByChatId(Long chatId) {
        return Optional.ofNullable(store.get(chatId));
    }

    @Override
    public void save(StateContext context) {
        store.put(context.chatId(), context);
    }
}
