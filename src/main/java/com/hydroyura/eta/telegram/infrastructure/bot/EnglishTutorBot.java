package com.hydroyura.eta.telegram.infrastructure.bot;

import com.hydroyura.eta.telegram.application.StateMachineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Objects;

@Slf4j
@Component
public class EnglishTutorBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final StateMachineService stateMachineService;

    public EnglishTutorBot(
        @Value("${telegram.bot.token}") String botToken,
        @Value("${telegram.bot.username}") String botUsername,
        StateMachineService stateMachineService
    ) {
        super(botToken);
        this.botUsername = botUsername;
        this.stateMachineService = stateMachineService;
    }

    @Override public String getBotUsername() { return botUsername; }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return;
        var msg = update.getMessage();
        var chatId = msg.getChatId();
        var text = msg.getText().trim();
        log.info("[{}] {}", chatId, text);
        try {
            var response = stateMachineService.process(chatId, text);
            if (Objects.nonNull(response)) sendMessage(chatId, response);
        } catch (Exception e) {
            log.error("Error", e);
            sendMessage(chatId, "❌ " + e.getMessage());
        }
    }

    private void sendMessage(Long chatId, String text) {
        try { execute(new SendMessage(chatId.toString(), text)); }
        catch (TelegramApiException e) { log.error("Send failed", e); }
    }
}
