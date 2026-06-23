package com.hydroyura.eta.chatbot.domain.statemachine;

import com.hydroyura.eta.teacher.api.teacher.TeacherId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ActiveContext extends Context {

    private final TeacherId teacherId;

}
