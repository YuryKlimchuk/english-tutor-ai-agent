package com.hydroyura.eta.chatbot.application;

import com.hydroyura.eta.chatbot.application.commands.*;
import com.hydroyura.eta.chatbot.domain.command.Command;
import com.hydroyura.eta.chatbot.domain.command.CommandDispatcher;
import com.hydroyura.eta.dictionary.api.dictionary.AddWordToDictionary;
import com.hydroyura.eta.student.api.lesson.*;
import com.hydroyura.eta.student.api.student.StudentQuery;
import com.hydroyura.eta.teacher.api.teacher.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandDispatcherImpl implements CommandDispatcher {

    private final Map<Class<? extends Command>, Command> instances = new HashMap<>();
    private final Map<Class<? extends Command>, String> prefixes = new HashMap<>();

    private final RegisterTeacher registerTeacher;
    private final CreateStudentWithDictionary createStudentWithDictionary;
    private final StartLesson startLesson;
    private final AddWordToDictionary addWordToDictionary;
    private final AddWordToLesson addWordToLesson;
    private final EndLesson endLesson;
    private final FindTeacher findTeacher;
    private final StudentQuery studentQuery;

    public CommandDispatcherImpl(RegisterTeacher r, CreateStudentWithDictionary c, StartLesson sl,
                                  AddWordToDictionary awd, AddWordToLesson awl, EndLesson el,
                                  FindTeacher f, StudentQuery sq) {
        this.registerTeacher = r; this.createStudentWithDictionary = c; this.startLesson = sl;
        this.addWordToDictionary = awd; this.addWordToLesson = awl; this.endLesson = el;
        this.findTeacher = f; this.studentQuery = sq;

        prefixes.put(StartCmd.class, "/start");
        prefixes.put(RegisterCmd.class, "/register");
        prefixes.put(NewStudentCmd.class, "/newstudent");
        prefixes.put(StartLessonCmd.class, "/startlesson");
        prefixes.put(AddWordCmd.class, "/add");
        prefixes.put(EndLessonCmd.class, "/endlesson");
        prefixes.put(HelpCmd.class, "/help");
    }

    @Override public Command dispatch(String text) {
        if (text.equals("/start")) return new StartCmd(findTeacher);
        if (text.startsWith("/register")) return new RegisterCmd(text, registerTeacher);
        if (text.startsWith("/newstudent")) return new NewStudentCmd(text, createStudentWithDictionary, findTeacher);
        if (text.startsWith("/startlesson")) return new StartLessonCmd(text, startLesson, findTeacher, studentQuery);
        if (text.startsWith("/add")) return new AddWordCmd(text, addWordToDictionary, addWordToLesson, studentQuery);
        if (text.equals("/endlesson")) return new EndLessonCmd(endLesson);
        if (text.equals("/help")) return new HelpCmd();
        return null;
    }

    @Override public Command get(Class<? extends Command> clazz) {
        return instances.computeIfAbsent(clazz, c -> {
            if (c == StartCmd.class) return new StartCmd(findTeacher);
            if (c == HelpCmd.class) return new HelpCmd();
            if (c == EndLessonCmd.class) return new EndLessonCmd(endLesson);
            if (c == RegisterCmd.class) return new RegisterCmd(registerTeacher);
            if (c == NewStudentCmd.class) return new NewStudentCmd(createStudentWithDictionary, findTeacher);
            if (c == StartLessonCmd.class) return new StartLessonCmd(startLesson, findTeacher, studentQuery);
            if (c == AddWordCmd.class) return new AddWordCmd(addWordToDictionary, addWordToLesson, studentQuery);
            return null;
        });
    }

    @Override public String getCommandPrefix(Class<? extends Command> clazz) {
        return prefixes.get(clazz);
    }
}
