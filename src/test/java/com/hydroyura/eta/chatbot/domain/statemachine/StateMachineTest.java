package com.hydroyura.eta.chatbot.domain.statemachine;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StateMachineTest {

    @Test
    void shouldStartInUnregisteredState() {
        var sm = new StateMachine(123L);
        assertThat(sm.getState()).isEqualTo(State.UNREGISTERED);
    }

    @Test
    void shouldApplyCommandAndTransition() {
        var sm = new StateMachine(123L);

        // Simulate register command that transitions to ACTIVE
        var result = sm.applyCommand(new Command() {
            public CommandType type() { return CommandType.REGISTER; }
            public ExecutionResult execute(StateMachine m) {
                return new ExecutionResult(State.ACTIVE, new ActiveContext(null), "OK");
            }
        });

        assertThat(result).isEqualTo("OK");
        assertThat(sm.getState()).isEqualTo(State.ACTIVE);
        assertThat(sm.getContext()).isNotNull();
    }

    @Test
    void shouldAllowValidCommand() {
        var sm = new StateMachine(123L);
        assertThat(State.UNREGISTERED.allows(CommandType.START)).isTrue();
    }
}
