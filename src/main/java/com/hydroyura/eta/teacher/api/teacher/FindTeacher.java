package com.hydroyura.eta.teacher.api.teacher;

import java.util.Optional;

public interface FindTeacher {

    Optional<TeacherId> findByTelegramChatId(Long chatId);
}
