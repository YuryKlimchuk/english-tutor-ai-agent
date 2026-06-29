package com.hydroyura.eta.chatbot.application.commands;

import com.hydroyura.eta.chatbot.domain.statemachine.*;
import com.hydroyura.eta.dictionary.api.dictionary.AddWordCommand;
import com.hydroyura.eta.dictionary.api.dictionary.AddWordToDictionary;
import com.hydroyura.eta.dictionary.api.word.PartOfSpeech;
import com.hydroyura.eta.student.api.lesson.AddWordToLesson;
import com.hydroyura.eta.student.api.lesson.AddWordToLessonCommand;
import com.hydroyura.eta.student.api.student.StudentQuery;
import java.util.*;

public class AddWordCmd implements Command {

    private final String word;
    private final PartOfSpeech pos;
    private final Set<String> translations;
    private final AddWordToDictionary addWordToDictionary;
    private final AddWordToLesson addWordToLesson;
    private final StudentQuery studentQuery;

    public AddWordCmd(String text, AddWordToDictionary awd, AddWordToLesson awl, StudentQuery sq) {
        var p = text.split(" ", 4);
        this.word = p.length > 1 ? p[1] : null;
        this.pos = parsePos(p.length > 2 ? p[2] : null);
        this.translations = p.length > 3 ? parseTranslations(p[3]) : Set.of();
        this.addWordToDictionary = awd;
        this.addWordToLesson = awl;
        this.studentQuery = sq;
    }

    @Override public CommandType type() { return CommandType.ADD_WORD; }

    @Override
    public ExecutionResult execute(StateMachine sm) {
        if (word == null || pos == null || translations.isEmpty()) {
            sm.setPendingCommand("/add");
            return new ExecutionResult(sm.getState(), sm.getContext(), "Enter: <word> <POS> <tr1; tr2>");
        }
        if (!(sm.getContext() instanceof LessonContext lc))
            return new ExecutionResult(sm.getState(), sm.getContext(), "No active lesson");
        var dictId = studentQuery.getDictionaryId(lc.getStudentId()).orElseThrow();
        var wid = addWordToDictionary.execute(new AddWordCommand(dictId, word, translations, pos));
        addWordToLesson.execute(new AddWordToLessonCommand(lc.getLessonId(), wid));
        return new ExecutionResult(sm.getState(), sm.getContext(), "✅ \"" + word + "\" added");
    }

    private static PartOfSpeech parsePos(String s) {
        try { return PartOfSpeech.valueOf(s.toUpperCase()); } catch (Exception e) { return null; }
    }

    private static Set<String> parseTranslations(String s) {
        var set = new HashSet<>(Arrays.asList(s.split(";")));
        set.removeIf(String::isBlank);
        return set;
    }
}
