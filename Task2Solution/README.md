# Автотесты для микросервиса объявлений (Avito QA Internship)

Репозиторий содержит автотесты на **Java + JUnit 5 + RestAssured** для микросервиса объявлений
из задания 2.1.

## Технологии

- Java 17
- Maven
- JUnit 5
- RestAssured

## Структура

- `pom.xml` — описание Maven-проекта
- `src/test/java/com/avito/qa/BaseApiTest.java` — базовый класс, настройка RestAssured, константы путей
- `src/test/java/com/avito/qa/AdsApiTest.java` — автотесты для:
  - создания объявления
  - получения объявления по `id`
  - получения объявлений по `sellerId`
  - получения статистики по `itemId`
- `TESTCASES.md` — список ручных тест-кейсов
- `BUGS.md` — найденные дефекты по результатам тестирования

## Запуск тестов

Требуется установленный **Java 17+** и **Maven**.

```bash
mvn test
