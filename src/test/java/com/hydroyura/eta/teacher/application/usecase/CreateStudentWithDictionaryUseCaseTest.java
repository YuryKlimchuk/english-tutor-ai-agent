package com.hydroyura.eta.teacher.application.usecase;

import com.hydroyura.eta.dictionary.api.dictionary.CreateDictionary;
import com.hydroyura.eta.dictionary.api.dictionary.CreateDictionaryCommand;
import com.hydroyura.eta.dictionary.api.dictionary.DictionaryId;
import com.hydroyura.eta.student.api.student.CreateStudent;
import com.hydroyura.eta.student.api.student.CreateStudentCommand;
import com.hydroyura.eta.student.api.student.StudentId;
import com.hydroyura.eta.teacher.api.teacher.CreateStudentWithDictionaryCommand;
import com.hydroyura.eta.teacher.api.teacher.TeacherId;
import com.hydroyura.eta.teacher.domain.teacher.Teacher;
import com.hydroyura.eta.teacher.domain.teacher.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreateStudentWithDictionaryUseCaseTest {

    private CreateStudentWithDictionaryUseCase useCase;
    private StubTeacherRepository teacherRepository;

    @BeforeEach
    void setUp() {
        teacherRepository = new StubTeacherRepository();

        var createDictionary = (CreateDictionary) cmd -> DictionaryId.generate();
        var createStudent = (CreateStudent) cmd -> StudentId.generate();

        useCase = new CreateStudentWithDictionaryUseCase(teacherRepository, createDictionary, createStudent);
    }

    @Test
    void shouldCreateStudentAndAddToTeacher() {
        var teacherId = TeacherId.generate();
        teacherRepository.save(Teacher.create(teacherId, "John"));

        var cmd = new CreateStudentWithDictionaryCommand(teacherId, "Иван", "Словарь Ивана");
        var studentId = useCase.execute(cmd);

        assertThat(studentId).isNotNull();
        var teacher = teacherRepository.findById(teacherId).orElseThrow();
        assertThat(teacher.getStudentIds()).contains(studentId);
    }

    @Test
    void shouldThrowWhenTeacherNotFound() {
        var cmd = new CreateStudentWithDictionaryCommand(TeacherId.generate(), "Иван", "Словарь");

        assertThatThrownBy(() -> useCase.execute(cmd))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Teacher not found");
    }

    // --- stub ---

    static class StubTeacherRepository implements TeacherRepository {
        private final Map<TeacherId, Teacher> store = new HashMap<>();

        @Override public Teacher save(Teacher t) { store.put(t.getId(), t); return t; }
        @Override public Optional<Teacher> findById(TeacherId id) { return Optional.ofNullable(store.get(id)); }
    }
}
