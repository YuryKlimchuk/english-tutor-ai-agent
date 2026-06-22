package com.hydroyura.eta.dictionary.domain.word;

import java.util.Objects;
import java.util.UUID;

public record WordId(UUID value) {

    public WordId {
        Objects.requireNonNull(value, "WordId must not be null");
    }

    public static WordId generate() {
        return new WordId(UUID.randomUUID());
    }
}
