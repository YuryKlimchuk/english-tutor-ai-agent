package com.hydroyura.eta.telegram.infrastructure.bot.statemachine;

import java.util.Optional;

public interface StateRepository {

    Optional<StateContext> findByChatId(Long chatId);

    void save(StateContext context);
}
