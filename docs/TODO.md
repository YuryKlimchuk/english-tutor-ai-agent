# TODO

## Срочно

- [x] Запуск приложения — исправлено: Spring Modulith 1.1.10 → 2.1.0 (несовместимость с SB 4.1.0)
- [ ] `ModuleVerificationTest.verifyModularity` — Modulith 2.1.0 ложно ругается на разрешённые зависимости
- [ ] Миграция teacher и exercise на новую структуру (`api/teacher/`, `domain/teacher/`)

## Модули

- [ ] **Student** — factory + validation specs
- [ ] **Teacher** — use case создания Student (оркестрирует создание Dictionary → Student)
- [ ] **Lesson** — спроектировать и реализовать
- [ ] **Exercise** — наполнить

## Инфраструктура

- [ ] JPA entities + репозитории (замена in-memory)
- [ ] PostgreSQL + Flyway миграции
- [ ] Spring AI интеграция

## Рефакторинг

- [ ] Миграция teacher → `api/teacher/`, `domain/teacher/` (сейчас в старом стиле `api/`, `domain/model/`)
- [ ] Миграция exercise → новый стиль
- [ ] Перенести `PartOfSpeech` в `shared/api/` когда понадобится Exercise'у

## Технический долг

- [ ] `EtaApplicationTests` — удалён из-за Spring Boot ClassNotFoundException, восстановить
- [ ] InMemoryDictionaryRepository — заменить на JPA
- [ ] InMemoryStudentRepository — заменить на JPA
- [ ] Wire AddWordToDictionaryUseCase в full context после появления JPA репозитория
