package com.hydroyura.eta.chatbot.application.commands;

import com.hydroyura.eta.chatbot.domain.command.Command;
import com.hydroyura.eta.chatbot.domain.command.Result;
import com.hydroyura.eta.chatbot.domain.statemachine.*;
import com.hydroyura.eta.teacher.api.teacher.RegisterTeacher;
import com.hydroyura.eta.teacher.api.teacher.RegisterTeacherCommand;

public class RegisterCmd implements Command {

    private final RegisterTeacher registerTeacher;

    public RegisterCmd(RegisterTeacher registerTeacher) {
        this.registerTeacher = registerTeacher;
    }

    // For initial dispatch with text
    public RegisterCmd(String text, RegisterTeacher registerTeacher) {
        this(registerTeacher);
    }

    @Override public CommandType type() { return CommandType.REGISTER; }

    @Override
    public Result execute(StateMachine sm, String userMessage) {
        // If coming from pending, this IS the name
        if (sm.getPendingCommandSafely().isPresent()) {
            return doRegister(sm, userMessage);
        }
        // From dispatch — userMessage is "/register Yury" or just "/register"
        if (userMessage.startsWith("/register ")) {
            return doRegister(sm, userMessage.substring(10).trim());
        }
        sm.setPendingCommand(RegisterCmd.class);
        return Result.stay("Enter your name:", type());
    }

    private Result doRegister(StateMachine sm, String name) {
        if (name.isBlank()) {
            sm.setPendingCommand(RegisterCmd.class);
            return Result.stay("Enter your name:", type());
        }
        registerTeacher.execute(new RegisterTeacherCommand(sm.getId().chatId(), name));
        return Result.transition("✅ Registered!", type(), State.ACTIVE, new Context());
    }
}
