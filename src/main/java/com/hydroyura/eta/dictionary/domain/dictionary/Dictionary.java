package com.hydroyura.eta.dictionary.domain.dictionary;

import com.hydroyura.eta.dictionary.api.dictionary.DictionaryId;
import com.hydroyura.eta.dictionary.domain.dictionary.exception.WordAlreadyExistsException;
import com.hydroyura.eta.dictionary.domain.word.Word;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;

import java.util.Set;

@Getter
@AggregateRoot
@AllArgsConstructor
public final class Dictionary {

    @Identity
    private final DictionaryId id;
    private final Set<Word> words;
    private String name;

    public void addWord(Word word) {
        if (word == null) {
            throw new IllegalArgumentException("Word must not be null");
        }
        if (words.stream().anyMatch(w -> w.getValue().equalsIgnoreCase(word.getValue()))) {
            throw new WordAlreadyExistsException(word.getValue());
        }
        this.words.add(word);
    }
}
