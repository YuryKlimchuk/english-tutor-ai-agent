package com.hydroyura.eta.teacher.api.teacher;

import com.hydroyura.eta.student.api.student.StudentId;

public interface CreateStudentWithDictionary {

    StudentId execute(CreateStudentWithDictionaryCommand command);
}
