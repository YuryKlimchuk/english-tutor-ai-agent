# Student

Ученик, создаваемый учителем. Привязан к словарю.

## Составляющие

### Student (Entity)
- `id`, `name`, `dictionaryId` (ссылка на Dictionary).
- Не знает про Teacher — связь Teacher → Student хранится в Teacher.
- Ученик не взаимодействует с системой напрямую.

## Use Cases

| Use Case | Статус |
|----------|--------|
| `CreateStudent` | ✅ |

## Зависимости
```java
@ApplicationModule(allowedDependencies = "dictionary")
```

## Тесты (6/6)
StudentTest (3), CreateStudentUseCaseTest (3)
