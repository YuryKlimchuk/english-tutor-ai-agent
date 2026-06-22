package com.hydroyura.eta.student.application.config;

import com.hydroyura.eta.student.api.student.CreateStudent;
import com.hydroyura.eta.student.application.usecase.CreateStudentUseCase;
import com.hydroyura.eta.student.domain.student.StudentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentModuleConfig {

    @Bean
    CreateStudent createStudent(StudentRepository repository) {
        return new CreateStudentUseCase(repository);
    }
}
