# Eta English tutor agent

## Version 1.0.0

This is application (AI agent) for helping English tutor in work. UI is implemented fia telegram bot.
In version 1.0.0 we must have follow functionality:
1. Teacher can start session (lesson) in tg bot and send to bot unknown words during lesson. Bot saves these words and
make reference with student.
2. After lesson (and in any other moment) teacher can get and send dictionary with words to student.
3. Teacher can generate via an agent exercise according specified topic with using words from dictionary.

### Using technologies:
1. Spring boot
2. Spring modulith
3. JMolecules
4. Spring AI
5. Postgres
6. MinIO

### Software architecture:
1. Modular monolit
2. Domain driven design

### Bounded contexts
1. Teacher
2. Student
3. Dictionary
4. Lesson
5. Exercise
6. A few support contexts (can be missed)