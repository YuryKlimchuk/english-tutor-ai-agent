package com.hydroyura.eta.telegram.infrastructure.bot.command;

import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class CommandDispatcher {

    private final Map<CommandType, BotCommand> commands = new EnumMap<>(CommandType.class);

    public CommandDispatcher(List<BotCommand> commandList) {
        commandList.forEach(c -> commands.put(c.type(), c));
    }

    public BotCommand dispatch(String text) {
        var cmd = resolveType(text);
        return commands.getOrDefault(cmd, commands.get(CommandType.HELP));
    }

    private CommandType resolveType(String text) {
        if (text.equals("/start")) return CommandType.START;
        if (text.startsWith("/register")) return CommandType.REGISTER;
        if (text.startsWith("/newstudent")) return CommandType.NEW_STUDENT;
        if (text.startsWith("/startlesson")) return CommandType.START_LESSON;
        if (text.startsWith("/add")) return CommandType.ADD_WORD;
        if (text.equals("/endlesson")) return CommandType.END_LESSON;
        if (text.equals("/help")) return CommandType.HELP;
        return CommandType.UNKNOWN;
    }
}
