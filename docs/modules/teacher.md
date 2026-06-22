# Teacher

Учитель. Управляет учениками, ведёт уроки.

## Составляющие

### Teacher (Entity)
- `id`, `name`, `studentIds` (@Association Set<StudentId>)

## Use Cases

| Use Case | Статус |
|----------|--------|
| `RegisterTeacher` | ✅ |
| `CreateStudentWithDictionary` | ✅ |

## Зависимости
```java
@ApplicationModule(allowedDependencies = {"dictionary", "student"})
```

## Тесты (5/5)
TeacherTest (1), RegisterTeacherUseCaseTest (2), CreateStudentWithDictionaryUseCaseTest (2)
