package com.hydroyura.eta.telegram.infrastructure.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class EnglishTutorBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final String botToken;

    public EnglishTutorBot(
        @Value("${telegram.bot.token}") String botToken,
        @Value("${telegram.bot.username}") String botUsername
    ) {
        super(botToken);
        this.botToken = botToken;
        this.botUsername = botUsername;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        var msg = update.getMessage();
        var chatId = msg.getChatId();
        var text = msg.getText();

        log.info("Received: {} from {}", text, chatId);

        var response = switch (text) {
            case "/start" -> "Welcome! I'm your English tutor bot.\nUse /register <name> to get started.";
            case "/help" -> "Commands:\n/start - Welcome\n/register <name> - Register as teacher";
            default -> "Unknown command: " + text + "\nType /help for available commands.";
        };

        sendMessage(chatId, response);
    }

    private void sendMessage(Long chatId, String text) {
        try {
            execute(new SendMessage(chatId.toString(), text));
        } catch (TelegramApiException e) {
            log.error("Failed to send message", e);
        }
    }
}
