package com.hydroyura.eta.telegram.infrastructure.bot;

import com.hydroyura.eta.teacher.api.teacher.RegisterTeacher;
import com.hydroyura.eta.teacher.api.teacher.RegisterTeacherCommand;
import com.hydroyura.eta.teacher.api.teacher.CreateStudentWithDictionary;
import com.hydroyura.eta.teacher.api.teacher.CreateStudentWithDictionaryCommand;
import com.hydroyura.eta.student.api.lesson.StartLesson;
import com.hydroyura.eta.student.api.lesson.StartLessonCommand;
import com.hydroyura.eta.teacher.domain.teacher.IdentifierType;
import com.hydroyura.eta.teacher.domain.teacher.TeacherRepository;
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
    private final RegisterTeacher registerTeacher;
    private final CreateStudentWithDictionary createStudentWithDictionary;
    private final StartLesson startLesson;
    private final TeacherRepository teacherRepository;

    public EnglishTutorBot(
        @Value("${telegram.bot.token}") String botToken,
        @Value("${telegram.bot.username}") String botUsername,
        RegisterTeacher registerTeacher,
        CreateStudentWithDictionary createStudentWithDictionary,
        StartLesson startLesson,
        TeacherRepository teacherRepository
    ) {
        super(botToken);
        this.botUsername = botUsername;
        this.registerTeacher = registerTeacher;
        this.createStudentWithDictionary = createStudentWithDictionary;
        this.startLesson = startLesson;
        this.teacherRepository = teacherRepository;
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
        var text = msg.getText().trim();

        log.info("[{}] {}", chatId, text);

        try {
            var response = handleCommand(chatId, text);
            if (response != null) {
                sendMessage(chatId, response);
            }
        } catch (Exception e) {
            log.error("Error handling command", e);
            sendMessage(chatId, "❌ " + e.getMessage());
        }
    }

    private String handleCommand(Long chatId, String text) {
        if (text.startsWith("/start")) {
            return "Welcome! /register <name> — register as teacher";
        }
        if (text.startsWith("/register")) {
            return handleRegister(chatId, text);
        }
        if (text.startsWith("/newstudent")) {
            return handleNewStudent(chatId, text);
        }
        if (text.startsWith("/startlesson")) {
            return handleStartLesson(chatId, text);
        }
        if (text.equals("/help")) {
            return helpText();
        }
        return "Unknown command. /help";
    }

    private String handleRegister(Long chatId, String text) {
        var existing = teacherRepository.findByIdentifier(IdentifierType.TELEGRAM, chatId);
        if (existing.isPresent()) {
            return "Already registered as " + existing.get().getName();
        }

        var parts = text.split(" ", 2);
        if (parts.length < 2) return "Usage: /register <name>";

        var name = parts[1].trim();
        registerTeacher.execute(new RegisterTeacherCommand(chatId, name));
        return "✅ Registered as " + name;
    }

    private String handleNewStudent(Long chatId, String text) {
        var teacher = teacherRepository.findByIdentifier(IdentifierType.TELEGRAM, chatId);
        if (teacher.isEmpty()) return "Register first: /register <name>";

        var parts = text.split(" ", 2);
        if (parts.length < 2) return "Usage: /newstudent <name>";

        var name = parts[1].trim();
        createStudentWithDictionary.execute(
            new CreateStudentWithDictionaryCommand(teacher.get().getId(), name, name + "'s Dictionary"));
        return "✅ Student " + name + " created";
    }

    private String handleStartLesson(Long chatId, String text) {
        var teacher = teacherRepository.findByIdentifier(IdentifierType.TELEGRAM, chatId);
        if (teacher.isEmpty()) return "Register first: /register <name>";

        var parts = text.split(" ", 2);
        if (parts.length < 2) return "Usage: /startlesson <student name>";

        // TODO: find student by name
        return "Not implemented yet";
    }

    private String helpText() {
        return """
            /register <name> — Register
            /newstudent <name> — Create student
            /startlesson <name> — Start lesson
            /help — Help
            """;
    }

    private void sendMessage(Long chatId, String text) {
        try {
            execute(new SendMessage(chatId.toString(), text));
        } catch (TelegramApiException e) {
            log.error("Failed to send message", e);
        }
    }
}
