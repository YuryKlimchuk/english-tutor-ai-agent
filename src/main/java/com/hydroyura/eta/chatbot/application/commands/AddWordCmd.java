package com.hydroyura.eta.chatbot.application.commands;

import com.hydroyura.eta.chatbot.domain.command.Command;
import com.hydroyura.eta.chatbot.domain.command.Result;
import com.hydroyura.eta.chatbot.domain.statemachine.*;
import com.hydroyura.eta.dictionary.api.dictionary.AddWordCommand;
import com.hydroyura.eta.dictionary.api.dictionary.AddWordToDictionary;
import com.hydroyura.eta.dictionary.api.word.PartOfSpeech;
import com.hydroyura.eta.student.api.lesson.AddWordToLesson;
import com.hydroyura.eta.student.api.lesson.AddWordToLessonCommand;
import com.hydroyura.eta.student.api.student.StudentQuery;
import java.util.*;

public class AddWordCmd implements Command {

    private static final String STEP = "addWord.step";
    private static final String WORD = "addWord.word";
    private static final String POS = "addWord.pos";

    private final AddWordToDictionary addWordToDictionary;
    private final AddWordToLesson addWordToLesson;
    private final StudentQuery studentQuery;

    public AddWordCmd(AddWordToDictionary awd, AddWordToLesson awl, StudentQuery sq) {
        this.addWordToDictionary = awd; this.addWordToLesson = awl; this.studentQuery = sq;
    }

    public AddWordCmd(String text, AddWordToDictionary awd, AddWordToLesson awl, StudentQuery sq) { this(awd, awl, sq); }

    @Override public CommandType type() { return CommandType.ADD_WORD; }
    @Override public boolean matches(String text) { return text.startsWith("/add"); }

    @Override
    public Result execute(StateMachine sm, String userMessage) {
        // If pending already set — continue multi-step
        if (sm.getPendingCommandSafely().isPresent())
            return handleStep(sm, userMessage);

        // If /add with data — parse it directly
        if (userMessage.startsWith("/add "))
            return handleStep(sm, userMessage.substring(5).trim());

        // Button click: /add without args — start multi-step
        sm.setPendingCommand(AddWordCmd.class);
        resetContext(sm);
        return Result.stay("Enter word:", type());
    }

    private Result handleStep(StateMachine sm, String input) {
        int step = getStep(sm);
        return switch (step) {
            case 0 -> stepWord(sm, input);
            case 1 -> stepPos(sm, input);
            case 2 -> stepTranslation(sm, input);
            default -> { sm.clearPendingCommand(); yield Result.stay("✅ Word added", type()); }
        };
    }

    private Result stepWord(StateMachine sm, String input) {
        if (input.isBlank()) return Result.stay("Enter word:", type());
        sm.getContext().put(WORD, input.trim());
        sm.getContext().put(STEP, 1);
        return Result.stay("Enter part of speech (NOUN/VERB/ADJECTIVE):", type());
    }

    private Result stepPos(StateMachine sm, String input) {
        try {
            PartOfSpeech.valueOf(input.trim().toUpperCase());
            sm.getContext().put(POS, input.trim().toUpperCase());
            sm.getContext().put(STEP, 2);
            return Result.stay("Enter translation:", type());
        } catch (IllegalArgumentException e) {
            return Result.stay("Invalid. Use NOUN, VERB or ADJECTIVE:", type());
        }
    }

    private Result stepTranslation(StateMachine sm, String input) {
        if (input.isBlank()) return Result.stay("Enter translation:", type());

        String word = (String) sm.getContext().get(WORD);
        String posStr = (String) sm.getContext().get(POS);
        PartOfSpeech pos = PartOfSpeech.valueOf(posStr);

        var translations = new HashSet<>(Arrays.asList(input.split(";")));
        translations.removeIf(String::isBlank);

        // TODO: integrate with student's dictionary + lesson
        // var dictId = studentQuery.getDictionaryId(...).orElseThrow();
        // var wid = addWordToDictionary.execute(new AddWordCommand(dictId, word, translations, pos));

        resetContext(sm);
        sm.clearPendingCommand();
        return Result.stay("✅ \"" + word + "\" (" + pos + ") added", type());
    }

    private int getStep(StateMachine sm) {
        Object s = sm.getContext().get(STEP);
        return s instanceof Integer i ? i : 0;
    }

    private void resetContext(StateMachine sm) {
        sm.getContext().put(STEP, 0);
        sm.getContext().put(WORD, null);
        sm.getContext().put(POS, null);
    }
}
