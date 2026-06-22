package com.hydroyura.eta.teacher.application.config;

import com.hydroyura.eta.dictionary.api.dictionary.CreateDictionary;
import com.hydroyura.eta.student.api.student.CreateStudent;
import com.hydroyura.eta.teacher.api.teacher.CreateStudentWithDictionary;
import com.hydroyura.eta.teacher.application.usecase.CreateStudentWithDictionaryUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeacherModuleConfig {

    @Bean
    CreateStudentWithDictionary createStudentWithDictionary(
        CreateDictionary createDictionary,
        CreateStudent createStudent
    ) {
        return new CreateStudentWithDictionaryUseCase(createDictionary, createStudent);
    }
}
