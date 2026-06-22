package com.hydroyura.eta.dictionary.domain.dictionary.exception;

import com.hydroyura.eta.dictionary.api.dictionary.DictionaryId;

public class DictionaryNotFoundException extends DictionaryDomainException {

    public DictionaryNotFoundException(DictionaryId id) {
        super("Dictionary not found: " + id.value());
    }
}
