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

## Полный цикл обработки сообщения

```mermaid
sequenceDiagram
    participant TG as Telegram
    participant B as EnglishTutorBot
    participant SVC as StateMachineService
    participant R as StateMachineRepository
    participant D as CommandDispatcher
    participant C as Command
    participant SM as StateMachine
    participant API as Business API

    TG->>B: Update (chatId, text)
    B->>SVC: process(chatId, text)
    SVC->>R: findByChatId(chatId)
    R-->>SVC: StateMachine (state, context, pending)
    
    alt pendingCommand != null & text не команда
        SVC->>SVC: text = pendingCommand + " " + text
        SVC->>SM: clearPendingCommand()
    end
    
    SVC->>D: dispatch(text)
    D-->>SVC: Command
    SVC->>SM: applyCommand(command)
    
    alt state.allows(command.type)
        SM->>C: execute(this)
        C->>API: use case call
        API-->>C: result
        C-->>SM: ExecutionResult(state, context, message)
        SM->>SM: updateState + updateContext
    else not allowed
        SM-->>SVC: "Not available"
    end
    
    SVC->>R: save(stateMachine)
    SVC-->>B: response text
    B->>SM: getState()
    SM-->>B: current state
    B->>TG: SendMessage(text + keyboard(state))
```

## Архитектура взаимодействия

```mermaid
flowchart TB
    subgraph Telegram
        U[Пользователь]
    end

    subgraph "Chatbot Module"
        subgraph Infrastructure
            B[EnglishTutorBot]
        end

        subgraph Application
            SVC[StateMachineService]
            DISP[CommandDispatcher]
        end

        subgraph Domain
            SM[StateMachine]
            ST[State]
            CTX[Context]
            REPO[StateMachineRepository]
        end

        subgraph Commands
            C1[StartCmd]
            C2[RegisterCmd]
            C3[NewStudentCmd]
            C4[StartLessonCmd]
            C5[AddWordCmd]
            C6[EndLessonCmd]
            C7[HelpCmd]
        end
    end

    subgraph "Business Modules"
        TEACHER[Teacher API]
        STUDENT[Student API]
        DICT[Dictionary API]
    end

    U -->|"/register"| B
    B -->|process| SVC
    SVC -->|load/save| REPO
    SVC -->|dispatch| DISP
    DISP -->|create| C2
    C2 -->|execute| SM
    C2 -->|calls| TEACHER
    SM -->|state + context| ST
    SM -->|context| CTX
```
