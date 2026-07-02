package com.hydroyura.eta.chatbot2.domain.command;

import com.hydroyura.eta.chatbot2.domain.statemachine.StateMachine;

public interface Command {

    Result execute(StateMachine sm);

}
