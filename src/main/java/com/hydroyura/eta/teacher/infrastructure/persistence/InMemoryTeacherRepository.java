package com.hydroyura.eta.teacher.infrastructure.persistence;

import com.hydroyura.eta.teacher.api.teacher.TeacherId;
import com.hydroyura.eta.teacher.api.teacher.IdentifierType;
import com.hydroyura.eta.teacher.domain.teacher.Teacher;
import com.hydroyura.eta.teacher.domain.teacher.TeacherRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryTeacherRepository implements TeacherRepository {

    private final Map<TeacherId, Teacher> store = new ConcurrentHashMap<>();

    @Override
    public Teacher save(Teacher teacher) {
        store.put(teacher.getId(), teacher);
        return teacher;
    }

    @Override
    public Optional<Teacher> findById(TeacherId id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Teacher> findByIdentifier(IdentifierType type, Object value) {
        return store.values().stream()
            .filter(t -> java.util.Objects.equals(value, t.getIdentifiers().get(type)))
            .findFirst();
    }
}
