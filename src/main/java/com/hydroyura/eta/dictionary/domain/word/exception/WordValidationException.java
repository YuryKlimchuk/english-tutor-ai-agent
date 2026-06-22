package com.hydroyura.eta.dictionary.domain.word.exception;

public class WordValidationException extends WordDomainException {

    public WordValidationException(String value) {
        super("Word validation failed: " + value);
    }
}
