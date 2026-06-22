package com.hydroyura.eta.dictionary.api.dictionary;

import java.util.Objects;
import java.util.UUID;

public record DictionaryId(UUID value) {

    public DictionaryId {
        Objects.requireNonNull(value, "DictionaryId must not be null");
    }

    public static DictionaryId generate() {
        return new DictionaryId(UUID.randomUUID());
    }
}
