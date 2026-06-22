package com.hydroyura.eta.dictionary.api.dictionary;

import com.hydroyura.eta.dictionary.api.word.WordId;

public interface AddWordToDictionary {

    WordId execute(AddWordCommand command);
}
