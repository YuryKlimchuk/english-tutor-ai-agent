package com.hydroyura.eta.chatbot.domain.statemachine;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StateTest {

    @Test
    void notRegisteredAllowsStartRegisterHelp() {
        assertThat(State.NOT_REGISTER.allows(CommandType.START)).isTrue();
        assertThat(State.NOT_REGISTER.allows(CommandType.REGISTER)).isTrue();
        assertThat(State.NOT_REGISTER.allows(CommandType.HELP)).isTrue();
    }

    @Test
    void notRegisteredRejectsOthers() {
        assertThat(State.NOT_REGISTER.allows(CommandType.END_LESSON)).isFalse();
        assertThat(State.NOT_REGISTER.allows(CommandType.ADD_WORD)).isFalse();
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
