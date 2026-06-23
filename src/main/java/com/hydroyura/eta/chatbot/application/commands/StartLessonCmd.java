package com.hydroyura.eta.chatbot.application.commands;

import com.hydroyura.eta.chatbot.domain.statemachine.*;
import com.hydroyura.eta.student.api.lesson.StartLesson;
import com.hydroyura.eta.student.api.lesson.StartLessonCommand;
import com.hydroyura.eta.student.api.student.FindStudentByNameQuery;
import com.hydroyura.eta.student.api.student.StudentId;
import com.hydroyura.eta.student.api.student.StudentQuery;
import com.hydroyura.eta.teacher.api.teacher.FindTeacher;
import com.hydroyura.eta.teacher.api.teacher.TeacherId;

public class StartLessonCmd implements Command {

    private final String studentName;
    private final StartLesson startLesson;
    private final FindTeacher findTeacher;
    private final StudentQuery studentQuery;

    public StartLessonCmd(String text, StartLesson s, FindTeacher f, StudentQuery sq) {
        var p = text.split(" ", 2);
        this.studentName = p.length > 1 ? p[1].trim() : null;
        this.startLesson = s;
        this.findTeacher = f;
        this.studentQuery = sq;
    }

    @Override public CommandType type() { return CommandType.START_LESSON; }

    @Override
    public ExecutionResult execute(StateMachine sm) {
        if (studentName == null || studentName.isEmpty())
            return new ExecutionResult(sm.getState(), sm.getContext(), "Usage: /startlesson <name>");
        var teacherId = findTeacher.findByTelegramChatId(sm.getChatId()).orElseThrow();
        var ids = findTeacher.getStudentIds(sm.getChatId());
        var sid = studentQuery.findByNameIn(new FindStudentByNameQuery(ids, studentName));
        if (sid.isEmpty())
            return new ExecutionResult(sm.getState(), sm.getContext(), "Student not found");
        var lid = startLesson.execute(new StartLessonCommand(sid.get(), "Lesson"));
        return new ExecutionResult(State.IN_LESSON,
            new LessonContext(teacherId, sid.get(), lid),
            "✅ Lesson started");
    }
}
