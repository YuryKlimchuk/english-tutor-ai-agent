package com.hydroyura.eta.teacher.application.usecase;

import com.hydroyura.eta.dictionary.api.dictionary.CreateDictionary;
import com.hydroyura.eta.dictionary.api.dictionary.CreateDictionaryCommand;
import com.hydroyura.eta.dictionary.api.dictionary.DictionaryId;
import com.hydroyura.eta.student.api.student.CreateStudent;
import com.hydroyura.eta.student.api.student.CreateStudentCommand;
import com.hydroyura.eta.student.api.student.StudentId;
import com.hydroyura.eta.teacher.api.teacher.CreateStudentWithDictionaryCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreateStudentWithDictionaryUseCaseTest {

    private CreateStudentWithDictionaryUseCase useCase;

    @BeforeEach
    void setUp() {
        // stubs
        var createDictionary = (CreateDictionary) cmd -> DictionaryId.generate();
        var createStudent = (CreateStudent) cmd -> StudentId.generate();

        useCase = new CreateStudentWithDictionaryUseCase(createDictionary, createStudent);
    }

    @Test
    void shouldCreateStudentWithDictionary() {
        var cmd = new CreateStudentWithDictionaryCommand("Иван", "Словарь Ивана");
        var studentId = useCase.execute(cmd);

        assertThat(studentId).isNotNull();
    }

    @Test
    void shouldReturnStudentId() {
        var cmd = new CreateStudentWithDictionaryCommand("Мария", "Словарь Марии");
        var result = useCase.execute(cmd);

        assertThat(result).isInstanceOf(StudentId.class);
    }
}
