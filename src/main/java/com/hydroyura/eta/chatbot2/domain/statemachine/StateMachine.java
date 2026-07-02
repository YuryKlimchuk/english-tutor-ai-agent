package com.hydroyura.eta.chatbot2.domain.statemachine;

import com.hydroyura.eta.chatbot2.domain.command.Command;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public final class StateMachine {

    private final StateMachineId id;

    private State state;

    private Class<? extends Command> pendingCommand;


    public void execute(Command command) {

    }

    public static StateMachine ofDefaults(StateMachineId id) {
        return new StateMachine(id);
    }

    public Optional<Class<? extends Command>> getPendingCommandSafely() {
        return Optional.ofNullable(pendingCommand);
    }

    public void clearPendingCommand() {
        pendingCommand = null;
    }

}
