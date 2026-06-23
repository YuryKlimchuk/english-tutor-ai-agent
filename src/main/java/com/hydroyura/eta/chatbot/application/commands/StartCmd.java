package com.hydroyura.eta.chatbot.application.commands;

import com.hydroyura.eta.chatbot.domain.statemachine.*;
import com.hydroyura.eta.teacher.api.teacher.FindTeacher;

public class StartCmd implements Command {

    private final FindTeacher findTeacher;

    public StartCmd(FindTeacher findTeacher) {
        this.findTeacher = findTeacher;
    }

    @Override public CommandType type() { return CommandType.START; }

    @Override
    public ExecutionResult execute(StateMachine sm) {
        if (findTeacher.findByTelegramChatId(sm.getChatId()).isPresent())
            return new ExecutionResult(sm.getState(), sm.getContext(), "Welcome back!");
        return new ExecutionResult(sm.getState(), sm.getContext(), "Welcome! /register <name>");
    }
}
