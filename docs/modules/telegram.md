# Telegram

Адаптер для Telegram бота. Принимает команды от учителя и делегирует доменным use case'ам.

## Команды

| Команда | UseCase | Пример |
|---------|---------|--------|
| `/start` | Приветствие | |
| `/register <name>` | RegisterTeacher | `/register John` |
| `/newstudent <name>` | CreateStudentWithDictionary | `/newstudent Иван` |
| `/startlesson <name>` | StartLesson | `/startlesson Иван` |
| `/add <word> <POS> <tr1; tr2>` | AddWordToDictionary + AddWordToLesson | `/add apple NOUN яблоко` |
| `/endlesson` | EndLesson | |
| `/help` | Помощь | |

## Зависимости
```java
@ApplicationModule(allowedDependencies = {
    "teacher :: teacher", "student :: student",
    "student :: lesson", "dictionary :: dictionary", "dictionary :: word"
})
```

## Статус
✅ Все основные команды работают.
