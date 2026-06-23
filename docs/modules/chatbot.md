# Chatbot

Модуль стейт-машин для бота. Платформонезависимый — Telegram, WhatsApp и т.д. подключаются через инфраструктуру.

## Составляющие

### StateMachine (Entity)
- `chatId`, `state`, `context`
- `applyCommand(Command)` — выполняет команду и обновляет состояние/контекст

### State (Enum)
- `UNREGISTERED`: START, REGISTER, HELP
- `ACTIVE`: START, NEW_STUDENT, START_LESSON, HELP
- `IN_LESSON`: ADD_WORD, END_LESSON, HELP

### Context
- `ActiveContext(teacherId)` — для ACTIVE
- `LessonContext(teacherId, studentId, lessonId)` — для IN_LESSON

## Use Cases (Application)
- `StateMachineService.process(chatId, text)` — load → dispatch → execute → save
- `CommandDispatcher.dispatch(text)` → Command

## Команды
- StartCmd, RegisterCmd, NewStudentCmd, StartLessonCmd, AddWordCmd, EndLessonCmd, HelpCmd
- Не бины, создаются `CommandDispatcher`'ом per-request

## Инфраструктура
- `EnglishTutorBot` — TelegramLongPollingBot
- `BotInitializer` — регистрирует бота
- `InMemoryStateMachineRepository`

## Зависимости
```java
@ApplicationModule(allowedDependencies = {
    "teacher :: teacher", "student :: student",
    "student :: lesson", "dictionary :: dictionary", "dictionary :: word"
})
```

## Тесты (2/2)
- StateMachineTest (3), StateTest (4)
