package com.hydroyura.eta.dictionary.domain.word;

import com.hydroyura.eta.shared.api.Specification;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class WordSpecifications {

    private final WordSpecificationConfig config;

    public Specification<Word> valueNotBlank() {
        return word -> word.getValue() != null && !word.getValue().isBlank();
    }

    public Specification<Word> valueMatchesPattern() {
        return word -> word.getValue() != null
            && word.getValue().matches(config.valueAllowedPattern());
    }

    public Specification<Word> valueLengthInRange() {
        return word -> word.getValue() != null
            && word.getValue().length() >= config.valueMinLength()
            && word.getValue().length() <= config.valueMaxLength();
    }

    public Specification<Word> translationsNotNull() {
        return word -> word.getTranslations() != null;
    }

    public Specification<Word> translationsNotEmpty() {
        return word -> !word.getTranslations().isEmpty();
    }

    public Specification<Word> hasMinTranslations() {
        return word -> word.getTranslations().size() >= config.minTranslations();
    }

    public Specification<Word> translationsMatchPattern() {
        return word -> word.getTranslations().stream()
            .allMatch(t -> t != null && t.matches(config.translationAllowedPattern()));
    }

    public Specification<Word> eachTranslationNotBlank() {
        return word -> word.getTranslations().stream()
            .allMatch(t -> t != null && !t.isBlank());
    }

    public Specification<Word> eachTranslationLengthInRange() {
        return word -> word.getTranslations().stream()
            .allMatch(t -> t != null
                && t.length() >= config.translationMinLength()
                && t.length() <= config.translationMaxLength());
    }

    public Specification<Word> partOfSpeechNotNull() {
        return word -> word.getPartOfSpeech() != null;
    }

    public Specification<Word> targetRepetitionsNotNull() {
        return word -> word.getTargetRepetitions() != null;
    }

    public Specification<Word> targetRepetitionsPositive() {
        return word -> word.getTargetRepetitions() != null
            && word.getTargetRepetitions() > 0;
    }

    public Specification<Word> targetRepetitionsNotExceedingMax() {
        return word -> word.getTargetRepetitions() != null
            && word.getTargetRepetitions() <= config.maxTargetRepetitions();
    }

    public Specification<Word> currentRepetitionsNotNull() {
        return word -> word.getCurrentRepetitions() != null;
    }

    public Specification<Word> currentRepetitionsNotNegative() {
        return word -> word.getCurrentRepetitions() != null
            && word.getCurrentRepetitions() >= 0;
    }

    public Specification<Word> validForCreation() {
        return valueNotBlank()
            .and(valueMatchesPattern())
            .and(valueLengthInRange())
            .and(translationsNotNull())
            .and(translationsNotEmpty())
            .and(hasMinTranslations())
            .and(eachTranslationNotBlank())
            .and(translationsMatchPattern())
            .and(eachTranslationLengthInRange())
            .and(partOfSpeechNotNull())
            .and(targetRepetitionsNotNull())
            .and(targetRepetitionsPositive())
            .and(targetRepetitionsNotExceedingMax())
            .and(currentRepetitionsNotNull())
            .and(currentRepetitionsNotNegative());
    }
}
