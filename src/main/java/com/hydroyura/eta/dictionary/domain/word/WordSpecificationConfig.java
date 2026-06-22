package com.hydroyura.eta.dictionary.domain.word;

public interface WordSpecificationConfig {

    String valueAllowedPattern();

    int valueMinLength();

    int valueMaxLength();

    String translationAllowedPattern();

    int translationMinLength();

    int translationMaxLength();

    int minTranslations();

    int maxTargetRepetitions();

    int defaultTargetRepetitions();
}
