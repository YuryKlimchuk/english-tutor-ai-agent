package com.hydroyura.eta.chatbot.domain.statemachine;

import lombok.Getter;

@Getter
public class StateMachine {

    private final Long chatId;
    private State state;
    private Context context;
    private String pendingCommand;

    public StateMachine(Long chatId) {
        this.chatId = chatId;
        this.state = State.UNREGISTERED;
        this.context = null;
    }

    private void updateState(State newState) { this.state = newState; }
    private void updateContext(Context context) { this.context = context; }

    public void setPendingCommand(String command) { this.pendingCommand = command; }
    public void clearPendingCommand() { this.pendingCommand = null; }

    public String applyCommand(Command command) {
        var result = command.execute(this);
        updateContext(result.context());
        updateState(result.state());
        return result.message();
    }
}
