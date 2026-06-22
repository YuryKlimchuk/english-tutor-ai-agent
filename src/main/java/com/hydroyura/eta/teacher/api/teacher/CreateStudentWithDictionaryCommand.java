package com.hydroyura.eta.teacher.api.teacher;

public record CreateStudentWithDictionaryCommand(
    String studentName,
    String dictionaryName
) {}
