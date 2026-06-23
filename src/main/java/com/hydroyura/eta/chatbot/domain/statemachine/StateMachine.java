package com.hydroyura.eta.chatbot.domain.statemachine;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StateMachine {

    private final Long chatId;

    private State state;

    private Context context;


    private void updateState(State newState) {
        this.state = newState;
    }

    private void updateContext(Context context) {
        this.context = context;
    }

    public String applyCommand(Command command) {
        var result = command.execute(this);
        updateContext(result.context());
        updateState(result.state());

        return result.message();
    }

}
