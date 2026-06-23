package com.hydroyura.eta.chatbot.application.commands;

import com.hydroyura.eta.chatbot.domain.statemachine.*;
import com.hydroyura.eta.teacher.api.teacher.RegisterTeacher;
import com.hydroyura.eta.teacher.api.teacher.RegisterTeacherCommand;

public class RegisterCmd implements Command {

    private final String name;
    private final RegisterTeacher registerTeacher;

    public RegisterCmd(String text, RegisterTeacher registerTeacher) {
        var p = text.split(" ", 2);
        this.name = p.length > 1 ? p[1].trim() : null;
        this.registerTeacher = registerTeacher;
    }

    @Override public CommandType type() { return CommandType.REGISTER; }

    @Override
    public ExecutionResult execute(StateMachine sm) {
        if (name == null || name.isEmpty()) {
            return new ExecutionResult(sm.getState(), sm.getContext(), "Usage: /register <name>");
        }
        var result = registerTeacher.execute(new RegisterTeacherCommand(sm.getChatId(), name));
        return new ExecutionResult(State.ACTIVE, new ActiveContext(result), "✅ Registered!");
    }
}
