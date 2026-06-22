package com.hydroyura.eta.student.infrastructure.persistence;

import com.hydroyura.eta.student.api.lesson.LessonId;
import com.hydroyura.eta.student.domain.student.Lesson;
import com.hydroyura.eta.student.domain.student.LessonRepository;
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
}
