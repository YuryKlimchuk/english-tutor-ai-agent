package com.hydroyura.eta.telegram.application;

import com.hydroyura.eta.dictionary.api.dictionary.AddWordCommand;
import com.hydroyura.eta.dictionary.api.dictionary.AddWordToDictionary;
import com.hydroyura.eta.dictionary.api.word.PartOfSpeech;
import com.hydroyura.eta.student.api.lesson.*;
import com.hydroyura.eta.student.api.student.FindStudentByNameQuery;
import com.hydroyura.eta.student.api.student.StudentId;
import com.hydroyura.eta.student.api.student.StudentQuery;
import com.hydroyura.eta.teacher.api.teacher.*;
import com.hydroyura.eta.telegram.domain.command.Command;
import com.hydroyura.eta.telegram.domain.statemachine.Session;
import com.hydroyura.eta.telegram.domain.statemachine.State;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CommandFactory {

    private final RegisterTeacher registerTeacher;
    private final CreateStudentWithDictionary createStudentWithDictionary;
    private final StartLesson startLesson;
    private final AddWordToDictionary addWordToDictionary;
    private final AddWordToLesson addWordToLesson;
    private final EndLesson endLesson;
    private final FindTeacher findTeacher;
    private final StudentQuery studentQuery;

    public CommandFactory(RegisterTeacher r, CreateStudentWithDictionary c, StartLesson s,
                          AddWordToDictionary awd, AddWordToLesson awl, EndLesson e,
                          FindTeacher f, StudentQuery sq) {
        this.registerTeacher = r; this.createStudentWithDictionary = c; this.startLesson = s;
        this.addWordToDictionary = awd; this.addWordToLesson = awl; this.endLesson = e;
        this.findTeacher = f; this.studentQuery = sq;
    }

    public Command create(Session session, String text) {
        if (text.equals("/start")) return s -> start(s);
        if (text.startsWith("/register")) return s -> register(s, text);
        if (text.startsWith("/newstudent")) return s -> newStudent(s, text);
        if (text.startsWith("/startlesson")) return s -> startLesson(s, text);
        if (text.startsWith("/add")) return s -> addWord(s, text);
        if (text.equals("/endlesson")) return s -> endLesson(s);
        if (text.equals("/help")) return s -> Command.Result.stay(help());
        return s -> Command.Result.stay("Unknown. /help");
    }

    private Command.Result start(Session s) {
        if (findTeacher.findByTelegramChatId(s.chatId()).isPresent())
            return Command.Result.stay("Welcome back!");
        return Command.Result.stay("Welcome! /register <name>");
    }

    private Command.Result register(Session s, String text) {
        if (s.state() != State.UNREGISTERED) return Command.Result.stay("Already registered!");
        var p = text.split(" ", 2);
        if (p.length < 2) return Command.Result.stay("Usage: /register <name>");
        registerTeacher.execute(new RegisterTeacherCommand(s.chatId(), p[1].trim()));
        return Command.Result.transition("✅ Registered!",
            new Session.Active(s.chatId(), Set.of()));
    }

    private Command.Result newStudent(Session s, String text) {
        if (s.state() != State.ACTIVE) return Command.Result.stay("Not available");
        var p = text.split(" ", 2);
        if (p.length < 2) return Command.Result.stay("Usage: /newstudent <name>");
        var tid = findTeacher.findByTelegramChatId(s.chatId()).orElseThrow();
        createStudentWithDictionary.execute(
            new CreateStudentWithDictionaryCommand(tid, p[1].trim(), p[1].trim() + "'s Dictionary"));
        return Command.Result.stay("✅ Student " + p[1].trim() + " created");
    }

    private Command.Result startLesson(Session s, String text) {
        if (s.state() != State.ACTIVE) return Command.Result.stay("Not available");
        var p = text.split(" ", 2);
        if (p.length < 2) return Command.Result.stay("Usage: /startlesson <name>");
        var ids = findTeacher.getStudentIds(s.chatId());
        var sid = studentQuery.findByNameIn(new FindStudentByNameQuery(ids, p[1].trim()));
        if (sid.isEmpty()) return Command.Result.stay("Student not found");
        var lid = startLesson.execute(new StartLessonCommand(sid.get(), "Lesson"));
        return Command.Result.transition("✅ Lesson started",
            new Session.Lesson(s.chatId(), sid.get(), lid));
    }

    private Command.Result addWord(Session s, String text) {
        if (!(s instanceof Session.Lesson l)) return Command.Result.stay("No active lesson");
        var p = text.split(" ", 4);
        if (p.length < 4) return Command.Result.stay("Usage: /add <word> <POS> <tr1; tr2>");
        PartOfSpeech pos;
        try { pos = PartOfSpeech.valueOf(p[2].toUpperCase()); }
        catch (Exception e) { return Command.Result.stay("Invalid POS"); }
        var trs = new HashSet<>(Arrays.asList(p[3].split(";")));
        trs.removeIf(String::isBlank);
        var dictId = studentQuery.getDictionaryId(l.studentId()).orElseThrow();
        var wid = addWordToDictionary.execute(new AddWordCommand(dictId, p[1], trs, pos));
        addWordToLesson.execute(new AddWordToLessonCommand(l.lessonId(), wid));
        return Command.Result.stay("✅ \"" + p[1] + "\" added");
    }

    private Command.Result endLesson(Session s) {
        if (!(s instanceof Session.Lesson l)) return Command.Result.stay("No active lesson");
        endLesson.execute(new EndLessonCommand(l.lessonId()));
        return Command.Result.transition("✅ Lesson ended",
            new Session.Active(s.chatId(), Set.of()));
    }

    private String help() { return "/register, /newstudent, /startlesson, /add, /endlesson"; }
}
