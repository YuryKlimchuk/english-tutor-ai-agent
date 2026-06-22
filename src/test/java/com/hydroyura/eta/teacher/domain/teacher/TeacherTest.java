package com.hydroyura.eta.teacher.domain.teacher;

import com.hydroyura.eta.teacher.api.teacher.TeacherId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TeacherTest {

    @Test
    void shouldCreateTeacher() {
        var teacher = Teacher.create(TeacherId.generate());

        assertThat(teacher.getId()).isNotNull();
    }
}
