package com.hydroyura.eta.telegram.infrastructure.bot.command;

import com.hydroyura.eta.dictionary.api.dictionary.AddWordToDictionary;
import com.hydroyura.eta.dictionary.api.dictionary.AddWordCommand;
import com.hydroyura.eta.dictionary.api.word.PartOfSpeech;
import com.hydroyura.eta.student.api.lesson.AddWordToLesson;
import com.hydroyura.eta.student.api.lesson.AddWordToLessonCommand;
import com.hydroyura.eta.student.api.student.StudentQuery;
import com.hydroyura.eta.telegram.infrastructure.bot.statemachine.StateContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class AddWordHandler implements BotCommand {

    private final AddWordToDictionary addWordToDictionary;
    private final AddWordToLesson addWordToLesson;
    private final StudentQuery studentQuery;

    @Override public CommandType type() { return CommandType.ADD_WORD; }

    @Override
    public CommandResult execute(StateContext ctx, String text) {
        if (!(ctx instanceof StateContext.Lesson lessonCtx)) {
            return CommandResult.ok("No active lesson");
        }

        var p = text.split(" ", 4);
        if (p.length < 4) return CommandResult.ok("Usage: /add <word> <POS> <tr1; tr2>");

        PartOfSpeech pos;
        try { pos = PartOfSpeech.valueOf(p[2].toUpperCase()); }
        catch (IllegalArgumentException e) { return CommandResult.ok("Invalid POS: " + p[2]); }

        var translations = new HashSet<>(Arrays.asList(p[3].split(";")));
        translations.removeIf(String::isBlank);

        var dictId = studentQuery.getDictionaryId(lessonCtx.studentId()).orElseThrow();
        var wordId = addWordToDictionary.execute(
            new AddWordCommand(dictId, p[1], translations, pos));
        addWordToLesson.execute(new AddWordToLessonCommand(lessonCtx.lessonId(), wordId));

        return CommandResult.ok("✅ \"" + p[1] + "\" added");
    }
}
