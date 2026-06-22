# Dictionary

Словарь принадлежит ученику. Содержит слова, собранные во время уроков.

## Составляющие

### Dictionary (AggregateRoot)
- Корень агрегата, владеет списком Word.
- Инварианты: не допускает дубликатов слов по value (case-insensitive).
- Связь со Student: Student ссылается на DictionaryId. Dictionary не знает про Student.

### Word (Entity)
- Слово с множеством переводов, частью речи и прогрессом изучения.
- Immutable: `id`, `value`, `translations`. Mutable: `partOfSpeech`, `targetRepetitions`, `currentRepetitions`.
- Статус вычисляется: NEW / IN_PROGRESS / LEARNED.

## Use Cases

| Use Case | Статус |
|----------|--------|
| `CreateDictionary` | ✅ |
| `AddWordToDictionary` | ✅ |

## Зависимости
```java
@ApplicationModule(allowedDependencies = "shared")
```

## Тесты (36/36)
WordTest (11), WordSpecificationsTest (10), WordFactoryTest (5),
DictionaryTest (3), AddWordToDictionaryUseCaseTest (3), CreateDictionaryUseCaseTest (1),
DictionaryModuleConfigTest (5)
