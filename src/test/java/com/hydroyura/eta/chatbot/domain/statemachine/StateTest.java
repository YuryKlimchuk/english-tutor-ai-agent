package com.hydroyura.eta.chatbot.domain.statemachine;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StateTest {

    @Test
    void unregisteredAllowsStartRegisterHelp() {
        assertThat(State.UNREGISTERED.allows(CommandType.START)).isTrue();
        assertThat(State.UNREGISTERED.allows(CommandType.REGISTER)).isTrue();
        assertThat(State.UNREGISTERED.allows(CommandType.HELP)).isTrue();
    }

    @Test
    void unregisteredRejectsOthers() {
        assertThat(State.UNREGISTERED.allows(CommandType.ADD_WORD)).isFalse();
        assertThat(State.UNREGISTERED.allows(CommandType.END_LESSON)).isFalse();
    }

    @Test
    void activeAllowsStudentAndLessonCommands() {
        assertThat(State.ACTIVE.allows(CommandType.NEW_STUDENT)).isTrue();
        assertThat(State.ACTIVE.allows(CommandType.START_LESSON)).isTrue();
    }

    @Test
    void inLessonAllowsAddWordAndEndLesson() {
        assertThat(State.IN_LESSON.allows(CommandType.ADD_WORD)).isTrue();
        assertThat(State.IN_LESSON.allows(CommandType.END_LESSON)).isTrue();
    }
}
