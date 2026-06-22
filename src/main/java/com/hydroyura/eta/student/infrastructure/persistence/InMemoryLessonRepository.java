package com.hydroyura.eta.student.infrastructure.persistence;

import com.hydroyura.eta.student.api.lesson.LessonId;
import com.hydroyura.eta.student.api.student.StudentId;
import com.hydroyura.eta.student.domain.student.Lesson;
import com.hydroyura.eta.student.domain.student.LessonRepository;
import com.hydroyura.eta.student.domain.student.LessonStatus;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryLessonRepository implements LessonRepository {

    private final Map<LessonId, Lesson> store = new ConcurrentHashMap<>();

    @Override
    public Lesson save(Lesson lesson) {
        store.put(lesson.getId(), lesson);
        return lesson;
    }

    @Override
    public Optional<Lesson> findById(LessonId id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Lesson> findActiveByStudentId(StudentId studentId) {
        return store.values().stream()
            .filter(l -> l.getStudentId().equals(studentId))
            .filter(l -> l.getStatus() == LessonStatus.ACTIVE)
            .findFirst();
    }
}
