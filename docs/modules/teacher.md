# Teacher

Учитель. Управляет учениками, ведёт уроки.

## Составляющие

### Teacher (Entity)
- `id` — скелет, будет дополнен при интеграции с Telegram.

## Use Cases

| Use Case | Статус |
|----------|--------|
| `CreateStudentWithDictionary` | ✅ |

### CreateStudentWithDictionary
Оркестрирует создание ученика вместе со словарём.

```mermaid
sequenceDiagram
    participant T as Teacher
    participant UC as TeacherUseCase
    participant CD as CreateDictionary
    participant CS as CreateStudent

    T->>UC: execute(CreateStudentWithDictionaryCommand)
    UC->>CD: execute(CreateDictionaryCommand)
    CD-->>UC: DictionaryId
    UC->>CS: execute(CreateStudentCommand)
    CS-->>UC: StudentId
    UC-->>T: StudentId
```

## Зависимости (Spring Modulith)
```java
@ApplicationModule(allowedDependencies = {"dictionary", "student"})
```

## Тесты (3/3)
- TeacherTest (1), CreateStudentWithDictionaryUseCaseTest (2)
