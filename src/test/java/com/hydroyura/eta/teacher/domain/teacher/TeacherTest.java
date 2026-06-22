package com.hydroyura.eta.teacher.domain.teacher;

import com.hydroyura.eta.teacher.api.teacher.TeacherId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TeacherTest {

    @Test
    void shouldCreateTeacher() {
        var teacher = Teacher.create(TeacherId.generate(), "John");
        teacher.getIdentifiers().put(IdentifierType.TELEGRAM, 123L);

        assertThat(teacher.getId()).isNotNull();
        assertThat(teacher.getName()).isEqualTo("John");
        assertThat((Long) teacher.getIdentifiers().get(IdentifierType.TELEGRAM)).isEqualTo(123L);
    }
}
