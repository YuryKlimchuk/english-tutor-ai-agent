# Student

Агрегат: Student + уроки (Lesson).

## Составляющие

### Student (@AggregateRoot)
- `id`, `name`, `dictionaryId` (@Association).
- Не знает про Teacher.

### Lesson (@Entity, внутри агрегата Student)
- `id`, `studentId` (@Association), `name`, `wordIds` (@Association Set<WordId>)
- `status`: ACTIVE / ENDED
- `startedAt`, `endedAt`
- Инварианты: нельзя добавить слово в ENDED, нельзя закончить ENDED

## Use Cases

| Use Case | Статус |
|----------|--------|
| `CreateStudent` | ✅ |
| `StartLesson` | ✅ |
| `AddWordToLesson` | ✅ |
| `EndLesson` | ✅ |

## Зависимости
```java
@ApplicationModule(allowedDependencies = "dictionary")
```

## Тесты (12/12)
StudentTest (3), LessonTest (5), CreateStudentUseCaseTest (3), LessonUseCasesTest (1)
