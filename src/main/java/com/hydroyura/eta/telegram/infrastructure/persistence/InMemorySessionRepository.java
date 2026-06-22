package com.hydroyura.eta.telegram.infrastructure.persistence;

import com.hydroyura.eta.telegram.domain.statemachine.Session;
import com.hydroyura.eta.telegram.domain.statemachine.SessionRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemorySessionRepository implements SessionRepository {

    private final Map<Long, Session> store = new ConcurrentHashMap<>();

    @Override
    public Optional<Session> findByChatId(Long chatId) {
        return Optional.ofNullable(store.get(chatId));
    }

    @Override
    public void save(Session session) {
        store.put(session.chatId(), session);
    }
}
