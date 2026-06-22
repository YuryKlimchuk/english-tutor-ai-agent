package com.hydroyura.eta.dictionary.domain.dictionary.exception;

public class WordAlreadyExistsException extends DictionaryDomainException {

    public WordAlreadyExistsException(String value) {
        super("Word already exists in dictionary: " + value);
    }
}
