package com.hydroyura.eta.dictionary.infrastructure.persistence;

import com.hydroyura.eta.dictionary.api.dictionary.DictionaryId;
import com.hydroyura.eta.dictionary.domain.dictionary.Dictionary;
import com.hydroyura.eta.dictionary.domain.dictionary.DictionaryRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryDictionaryRepository implements DictionaryRepository {

    private final Map<DictionaryId, Dictionary> store = new ConcurrentHashMap<>();

    @Override
    public Dictionary save(Dictionary dictionary) {
        store.put(dictionary.getId(), dictionary);
        return dictionary;
    }

    @Override
    public Optional<Dictionary> findById(DictionaryId id) {
        return Optional.ofNullable(store.get(id));
    }
}
