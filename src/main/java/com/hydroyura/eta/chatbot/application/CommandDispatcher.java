package com.hydroyura.eta.chatbot.application;

import com.hydroyura.eta.chatbot.domain.statemachine.Command;
import com.hydroyura.eta.chatbot.domain.statemachine.CommandType;
import com.hydroyura.eta.chatbot.domain.statemachine.ExecutionResult;
import com.hydroyura.eta.chatbot.domain.statemachine.StateMachine;
import com.hydroyura.eta.chatbot.application.commands.*;
import com.hydroyura.eta.dictionary.api.dictionary.AddWordToDictionary;
import com.hydroyura.eta.student.api.lesson.AddWordToLesson;
import com.hydroyura.eta.student.api.lesson.EndLesson;
import com.hydroyura.eta.student.api.lesson.StartLesson;
import com.hydroyura.eta.student.api.student.StudentQuery;
import com.hydroyura.eta.teacher.api.teacher.CreateStudentWithDictionary;
import com.hydroyura.eta.teacher.api.teacher.FindTeacher;
import com.hydroyura.eta.teacher.api.teacher.RegisterTeacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandDispatcher {

    private final RegisterTeacher registerTeacher;
    private final CreateStudentWithDictionary createStudentWithDictionary;
    private final StartLesson startLesson;
    private final AddWordToDictionary addWordToDictionary;
    private final AddWordToLesson addWordToLesson;
    private final EndLesson endLesson;
    private final FindTeacher findTeacher;
    private final StudentQuery studentQuery;

    public Command dispatch(String text) {
        if (text.equals("/start")) return new StartCmd(findTeacher);
        if (text.startsWith("/register")) return new RegisterCmd(text, registerTeacher);
        if (text.startsWith("/newstudent")) return new NewStudentCmd(text, createStudentWithDictionary, findTeacher);
        if (text.startsWith("/startlesson")) return new StartLessonCmd(text, startLesson, findTeacher, studentQuery);
        if (text.startsWith("/add")) return new AddWordCmd(text, addWordToDictionary, addWordToLesson, studentQuery);
        if (text.equals("/endlesson")) return new EndLessonCmd(endLesson);
        if (text.equals("/help")) return new HelpCmd();
        return new Command() {
            @Override public CommandType type() { return CommandType.UNKNOWN; }
            @Override public ExecutionResult execute(StateMachine sm) {
                return new ExecutionResult(sm.getState(), sm.getContext(), "Unknown. /help");
            }
        };
    }
}
