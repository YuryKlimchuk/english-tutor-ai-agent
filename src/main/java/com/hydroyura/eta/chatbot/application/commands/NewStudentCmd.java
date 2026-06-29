package com.hydroyura.eta.chatbot.application.commands;

import com.hydroyura.eta.chatbot.domain.statemachine.*;
import com.hydroyura.eta.teacher.api.teacher.CreateStudentWithDictionary;
import com.hydroyura.eta.teacher.api.teacher.CreateStudentWithDictionaryCommand;
import com.hydroyura.eta.teacher.api.teacher.FindTeacher;

public class NewStudentCmd implements Command {

    private final String name;
    private final CreateStudentWithDictionary createStudentWithDictionary;
    private final FindTeacher findTeacher;

    public NewStudentCmd(String text, CreateStudentWithDictionary c, FindTeacher f) {
        var p = text.split(" ", 2);
        this.name = p.length > 1 ? p[1].trim() : null;
        this.createStudentWithDictionary = c;
        this.findTeacher = f;
    }

    @Override public CommandType type() { return CommandType.NEW_STUDENT; }

    @Override
    public ExecutionResult execute(StateMachine sm) {
        if (name == null || name.isEmpty()) {
            sm.setPendingCommand("/newstudent");
            return new ExecutionResult(sm.getState(), sm.getContext(), "Enter student name:");
        }
        var tid = findTeacher.findByTelegramChatId(sm.getChatId()).orElseThrow();
        createStudentWithDictionary.execute(
            new CreateStudentWithDictionaryCommand(tid, name, name + "'s Dictionary"));
        return new ExecutionResult(sm.getState(), sm.getContext(), "✅ Student " + name + " created");
    }
}
