package com.hydroyura.eta.dictionary.domain.word.exception;

public class LastTranslationException extends WordDomainException {

    public LastTranslationException() {
        super("Cannot remove the last translation");
    }
}
