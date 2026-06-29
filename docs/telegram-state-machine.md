# Telegram Bot State Machine

## Состояния

```
UNREGISTERED → ACTIVE → IN_LESSON → ACTIVE → ...
```

| State | Контекст | Допустимые команды |
|-------|----------|-------------------|
| `UNREGISTERED` | — | `/start`, `/register`, `/help` |
| `ACTIVE` | `ActiveContext(teacherId)` | `/newstudent`, `/startlesson`, `/help` |
| `IN_LESSON` | `LessonContext(teacherId, studentId, lessonId)` | `/add`, `/endlesson`, `/help` |

## Двухфазный ввод

Команды с аргументами (`/register`, `/newstudent`, `/startlesson`, `/add`) работают в два шага:

```mermaid
sequenceDiagram
    U->>B: нажимает /register (кнопка)
    B->>SM: process("/register")
    SM->>SM: pendingCommand = "/register"
    B-->>U: "Enter your name:"
    U->>B: Yury
    B->>SM: process("Yury")
    SM->>SM: text = "/register Yury", clearPending
    B-->>U: "✅ Registered!" + кнопки ACTIVE
```

1. **Кнопка** — бот сохраняет `pendingCommand` и просит ввод
2. **Текст** — бот подставляет `pendingCommand + " " + текст` и выполняет

## Граф переходов

```mermaid
stateDiagram-v2
    [*] --> UNREGISTERED
    UNREGISTERED --> ACTIVE: /register <name>
    ACTIVE --> IN_LESSON: /startlesson <name>
    IN_LESSON --> ACTIVE: /endlesson
```
