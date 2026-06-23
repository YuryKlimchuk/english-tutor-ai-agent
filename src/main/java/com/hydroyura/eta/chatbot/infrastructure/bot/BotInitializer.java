package com.hydroyura.eta.chatbot.infrastructure.bot;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@RequiredArgsConstructor
public class BotInitializer {

    private final EnglishTutorBot bot;

    @PostConstruct
    public void init() throws Exception {
        new TelegramBotsApi(DefaultBotSession.class).registerBot(bot);
    }
}
