package com.hydroyura.eta.teacher.domain.teacher;

public enum IdentifierType {
    TELEGRAM(Long.class);

    private final Class<?> valueClass;

    IdentifierType(Class<?> valueClass) {
        this.valueClass = valueClass;
    }

    public Class<?> getValueClass() {
        return valueClass;
    }
}
