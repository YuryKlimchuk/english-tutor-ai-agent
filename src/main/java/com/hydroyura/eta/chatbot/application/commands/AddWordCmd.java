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

    private final AddWordToDictionary addWordToDictionary;
    private final AddWordToLesson addWordToLesson;
    private final StudentQuery studentQuery;

    public AddWordCmd(AddWordToDictionary awd, AddWordToLesson awl, StudentQuery sq) {
        this.addWordToDictionary = awd; this.addWordToLesson = awl; this.studentQuery = sq;
    }

    public AddWordCmd(String text, AddWordToDictionary awd, AddWordToLesson awl, StudentQuery sq) { this(awd, awl, sq); }

    @Override public CommandType type() { return CommandType.ADD_WORD; }

    @Override
    public Result execute(StateMachine sm, String userMessage) {
        if (sm.getPendingCommandSafely().isPresent() || !userMessage.startsWith("/add "))
            return doAdd(sm, userMessage);
        return doAdd(sm, userMessage.substring(5).trim());
    }

    private Result doAdd(StateMachine sm, String input) {
        var p = input.split(" ", 3);
        if (p.length < 3) { sm.setPendingCommand(AddWordCmd.class); return Result.stay("Enter: <word> <POS> <tr1; tr2>", type()); }
        PartOfSpeech pos;
        try { pos = PartOfSpeech.valueOf(p[1].toUpperCase()); }
        catch (Exception e) { sm.setPendingCommand(AddWordCmd.class); return Result.stay("Invalid POS: " + p[1], type()); }
        var trs = new HashSet<>(Arrays.asList(p[2].split(";")));
        trs.removeIf(String::isBlank);
        // TODO: integrate with student's dictionary
        return Result.stay("✅ \"" + p[0] + "\" added", type());
    }
}
