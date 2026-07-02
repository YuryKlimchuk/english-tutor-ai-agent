package com.hydroyura.eta.chatbot2.domain.command;


public interface CommandDispatcher {

    Command dispatch(String command);

    Command get(Class<? extends Command> clazz);

}
