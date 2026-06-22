package com.hydroyura.eta.teacher.application.usecase;

import com.hydroyura.eta.teacher.api.teacher.FindTeacher;
import com.hydroyura.eta.teacher.api.teacher.IdentifierType;
import com.hydroyura.eta.teacher.api.teacher.TeacherId;
import com.hydroyura.eta.teacher.domain.teacher.TeacherRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class FindTeacherService implements FindTeacher {

    private final TeacherRepository teacherRepository;

    @Override
    public Optional<TeacherId> findByTelegramChatId(Long chatId) {
        return teacherRepository.findByIdentifier(IdentifierType.TELEGRAM, chatId)
            .map(t -> t.getId());
    }
}
