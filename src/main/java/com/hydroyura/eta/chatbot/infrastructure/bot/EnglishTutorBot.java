package com.hydroyura.eta.chatbot.infrastructure.bot;

import com.hydroyura.eta.chatbot.application.StateMachineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public String getBotUsername() { return botUsername; }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return;
        var msg = update.getMessage();
        var chatId = msg.getChatId();
        var text = msg.getText().trim();
        log.info("[{}] {}", chatId, text);

        try {
            var response = stateMachineService.process(chatId, text);
            if (Objects.nonNull(response)) {
                var state = stateMachineService.getState(chatId);
                sendMessage(chatId, response, state.keyboardButtons());
            }
        } catch (Exception e) {
            log.error("Error processing message", e);
            sendMessage(chatId, "❌ Something went wrong");
        }
    }

    private void sendMessage(Long chatId, String text, String[] buttons) {
        try {
            var msg = new SendMessage(chatId.toString(), text);
            if (buttons.length > 0) {
                var keyboard = new ArrayList<KeyboardRow>();
                var row = new KeyboardRow();
                for (var btn : buttons) { row.add(btn); }
                keyboard.add(row);
                var markup = ReplyKeyboardMarkup.builder()
                    .keyboard(keyboard)
                    .resizeKeyboard(true)
                    .oneTimeKeyboard(false)
                    .build();
                msg.setReplyMarkup(markup);
            }
            execute(msg);
        } catch (TelegramApiException e) {
            log.error("Failed to send message", e);
        }
    }

    private void sendMessage(Long chatId, String text) {
        sendMessage(chatId, text, new String[0]);
    }
}
