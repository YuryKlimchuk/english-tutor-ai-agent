package com.hydroyura.eta.telegram.application;

import com.hydroyura.eta.telegram.domain.command.Command;
import com.hydroyura.eta.telegram.domain.statemachine.Session;
import com.hydroyura.eta.telegram.domain.statemachine.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class StateMachineService {

    private final SessionRepository sessionRepository;
    private final CommandFactory commandFactory;

    public StateMachineService(SessionRepository sessionRepository, CommandFactory commandFactory) {
        this.sessionRepository = sessionRepository;
        this.commandFactory = commandFactory;
    }

    public String process(Long chatId, String text) {
        var session = sessionRepository.findByChatId(chatId)
            .orElseGet(() -> new Session.Unregistered(chatId));
        var command = commandFactory.create(session, text);

        var result = command.run(session);
        var newSession = result.newSession() != null ? result.newSession() : session;
        sessionRepository.save(newSession);

        return result.text();
    }
}
