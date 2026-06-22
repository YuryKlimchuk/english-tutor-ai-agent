# Exercise

Упражнения, генерируемые на основе словаря ученика.

## Составляющие

### Exercise (Entity)
- `id` — пока скелет.

## Use Cases

Пока нет. Нужен: `GenerateExercise`.

## Зависимости (Spring Modulith)
```java
@ApplicationModule(allowedDependencies = "dictionary")
```

## Статус
⚠️ Старая структура (api/ в корне, domain/model/). Нужна миграция.
