package com.hydroyura.eta.telegram.domain.statemachine;

import java.util.Optional;

public interface SessionRepository {

    Optional<Session> findByChatId(Long chatId);

    void save(Session session);
}
