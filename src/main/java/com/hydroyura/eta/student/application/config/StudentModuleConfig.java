package com.hydroyura.eta.student.application.config;

import com.hydroyura.eta.student.api.lesson.AddWordToLesson;
import com.hydroyura.eta.student.api.student.CreateStudent;
import com.hydroyura.eta.student.api.lesson.EndLesson;
import com.hydroyura.eta.student.api.lesson.StartLesson;
import com.hydroyura.eta.student.application.usecase.AddWordToLessonUseCase;
import com.hydroyura.eta.student.application.usecase.CreateStudentUseCase;
import com.hydroyura.eta.student.application.usecase.EndLessonUseCase;
import com.hydroyura.eta.student.application.usecase.StartLessonUseCase;
import com.hydroyura.eta.student.domain.student.LessonRepository;
import com.hydroyura.eta.student.domain.student.StudentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentModuleConfig {

    @Bean
    CreateStudent createStudent(StudentRepository repository) {
        return new CreateStudentUseCase(repository);
    }

    @Bean
    StartLesson startLesson(LessonRepository repository) {
        return new StartLessonUseCase(repository);
    }

    @Bean
    AddWordToLesson addWordToLesson(LessonRepository repository) {
        return new AddWordToLessonUseCase(repository);
    }

    @Bean
    EndLesson endLesson(LessonRepository repository) {
        return new EndLessonUseCase(repository);
    }
}
