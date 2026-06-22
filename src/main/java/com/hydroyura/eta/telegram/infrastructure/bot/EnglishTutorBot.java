package com.hydroyura.eta.telegram.infrastructure.bot;

import com.hydroyura.eta.dictionary.api.dictionary.AddWordToDictionary;
import com.hydroyura.eta.dictionary.api.dictionary.AddWordCommand;
import com.hydroyura.eta.dictionary.api.word.PartOfSpeech;
import com.hydroyura.eta.dictionary.api.word.WordId;
import com.hydroyura.eta.student.api.lesson.*;
import com.hydroyura.eta.student.api.student.*;
import com.hydroyura.eta.teacher.api.teacher.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

@Slf4j
@Component
public class EnglishTutorBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final RegisterTeacher registerTeacher;
    private final CreateStudentWithDictionary createStudentWithDictionary;
    private final StartLesson startLesson;
    private final FindTeacher findTeacher;
    private final StudentQuery studentQuery;
    private final FindActiveLesson findActiveLesson;
    private final AddWordToDictionary addWordToDictionary;
    private final AddWordToLesson addWordToLesson;

    public EnglishTutorBot(
        @Value("${telegram.bot.token}") String botToken,
        @Value("${telegram.bot.username}") String botUsername,
        RegisterTeacher registerTeacher,
        CreateStudentWithDictionary createStudentWithDictionary,
        StartLesson startLesson,
        FindTeacher findTeacher,
        StudentQuery studentQuery,
        FindActiveLesson findActiveLesson,
        AddWordToDictionary addWordToDictionary,
        AddWordToLesson addWordToLesson
    ) {
        super(botToken);
        this.botUsername = botUsername;
        this.registerTeacher = registerTeacher;
        this.createStudentWithDictionary = createStudentWithDictionary;
        this.startLesson = startLesson;
        this.findTeacher = findTeacher;
        this.studentQuery = studentQuery;
        this.findActiveLesson = findActiveLesson;
        this.addWordToDictionary = addWordToDictionary;
        this.addWordToLesson = addWordToLesson;
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
            var r = handleCommand(chatId, text);
            if (Objects.nonNull(r)) sendMessage(chatId, r);
        } catch (Exception e) {
            log.error("Error", e);
            sendMessage(chatId, "❌ " + e.getMessage());
        }
    }

    private String handleCommand(Long chatId, String text) {
        if (text.startsWith("/start")) return handleStart(chatId);
        if (text.startsWith("/register")) return handleRegister(chatId, text);
        if (text.startsWith("/newstudent")) return handleNewStudent(chatId, text);
        if (text.startsWith("/startlesson")) return handleStartLesson(chatId, text);
        if (text.startsWith("/add")) return handleAddWord(chatId, text);
        if (text.equals("/help")) return helpText();
        return "Unknown. /help";
    }

    private String handleStart(Long chatId) {
        return findTeacher.findByTelegramChatId(chatId).isPresent()
            ? "Welcome back! /newstudent, /startlesson"
            : "Welcome! /register <name>";
    }

    private String handleRegister(Long chatId, String text) {
        if (findTeacher.findByTelegramChatId(chatId).isPresent()) return "Already registered!";
        var p = text.split(" ", 2);
        if (p.length < 2) return "Usage: /register <name>";
        registerTeacher.execute(new RegisterTeacherCommand(chatId, p[1].trim()));
        return "✅ Registered! /newstudent <name>";
    }

    private String handleNewStudent(Long chatId, String text) {
        var teacherId = findTeacher.findByTelegramChatId(chatId);
        if (teacherId.isEmpty()) return "Register first: /register <name>";
        var p = text.split(" ", 2);
        if (p.length < 2) return "Usage: /newstudent <name>";
        var name = p[1].trim();
        createStudentWithDictionary.execute(new CreateStudentWithDictionaryCommand(teacherId.get(), name, name + "'s Dictionary"));
        return "✅ Student " + name + " created";
    }

    private String handleStartLesson(Long chatId, String text) {
        var teacherId = findTeacher.findByTelegramChatId(chatId);
        if (teacherId.isEmpty()) return "Register first";
        var p = text.split(" ", 2);
        if (p.length < 2) return "Usage: /startlesson <student>";
        var name = p[1].trim();
        var ids = findTeacher.getStudentIds(chatId);
        var sid = studentQuery.findByNameIn(new FindStudentByNameQuery(ids, name));
        if (sid.isEmpty()) return "Student not found: " + name;
        var lid = startLesson.execute(new StartLessonCommand(sid.get(), "Lesson"));
        return "✅ Lesson started: " + lid.value();
    }

    private String handleAddWord(Long chatId, String text) {
        var teacherId = findTeacher.findByTelegramChatId(chatId);
        if (teacherId.isEmpty()) return "Register first";

        // Find active lesson
        var studentIds = findTeacher.getStudentIds(chatId);
        LessonId lessonId = null;
        StudentId studentId = null;
        for (var sid : studentIds) {
            var lid = findActiveLesson.findByStudentId(sid);
            if (lid.isPresent()) { lessonId = lid.get(); studentId = sid; break; }
        }
        if (lessonId == null) return "No active lesson. /startlesson first";

        // Parse: /add word POS trans1; trans2
        var p = text.substring(5).trim().split(" ", 3);
        if (p.length < 3) return "Usage: /add word POS translation1; translation2";

        var word = p[0];
        PartOfSpeech pos;
        try { pos = PartOfSpeech.valueOf(p[1].toUpperCase()); }
        catch (IllegalArgumentException e) { return "Invalid POS: " + p[1] + ". Use NOUN, VERB, ADJECTIVE"; }

        var translations = new HashSet<>(Arrays.asList(p[2].split(";")));
        translations.removeIf(String::isBlank);

        var dictId = studentQuery.getDictionaryId(studentId).orElseThrow();
        addWordToDictionary.execute(new AddWordCommand(dictId, word, translations, pos));

        // The dictionary use case creates a word but doesn't return the wordId.
        // For simplicity: skip addWordToLesson for now, or find the word.
        return "✅ Word \"" + word + "\" added";
    }

    private String helpText() {
        return "/register <name>, /newstudent <name>, /startlesson <name>, /add <word> <POS> <tr1; tr2>";
    }

    private void sendMessage(Long chatId, String text) {
        try { execute(new SendMessage(chatId.toString(), text)); }
        catch (TelegramApiException e) { log.error("Send failed", e); }
    }
}
