package com.hydroyura.eta.chatbot.domain.statemachine;

import com.hydroyura.eta.chatbot.domain.command.Command;
import com.hydroyura.eta.chatbot.domain.command.Result;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StateMachineTest {

    @Test
    void shouldStartInNotRegisteredState() {
        var sm = StateMachine.ofDefaults(new StateMachineId(123L));
        assertThat(sm.getState()).isEqualTo(State.NOT_REGISTER);
    }

    @Test
    void shouldExecuteCommand() {
        var sm = StateMachine.ofDefaults(new StateMachineId(123L));
        var result = sm.execute(new Command() {
            public CommandType type() { return CommandType.START; }
            public Result execute(StateMachine m, String msg) {
                return Result.stay("Hello", type());
            }
        }, "/start");
        assertThat(result).isEqualTo("Hello");
    }

    @Test
    void shouldRejectNotAllowedCommand() {
        var sm = StateMachine.ofDefaults(new StateMachineId(123L));
        var result = sm.execute(new Command() {
            public CommandType type() { return CommandType.ADD_WORD; }
            public Result execute(StateMachine m, String msg) {
                return Result.stay("nope", type());
            }
        }, "/add");
        assertThat(result).contains("not available");
    }
}
