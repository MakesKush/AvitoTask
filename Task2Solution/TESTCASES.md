# Тест-кейсы для API микросервиса объявлений

Сервис: `https://qa-internship.avito.com`  

Формат ответа в большинстве ручек:

```json
{
  "status": "ok" | "error",
  "result": { }
}
```

---

## 1. POST /api/1/item — создание объявления

### 1.1. Позитивные сценарии

#### TC-POST-01. Успешное создание объявления с корректными данными

- **Предусловие:** нет.  
- **Запрос:**
  ```http
  POST /api/1/item
  Content-Type: application/json
  ```
  ```json
  {
    "sellerID": 222222,
    "name": "Test product",
    "price": 500,
    "statistics": {
      "likes": 10,
      "viewCount": 100,
      "contacts": 5
    }
  }
  ```
- **Ожидаемый результат:**
  - HTTP `200 OK`;
  - `status = "ok"`;
  - в `result` присутствует `itemId` и основные поля объявления.
- **Фактический результат:** `200 OK`, `status = "ok"`, `result.itemId` возвращается.  
- **Статус:** Passed.

---

### 1.2. Негативные сценарии по `sellerID`

#### TC-POST-02. Отсутствует обязательное поле `sellerID`

- **Запрос:**
  ```json
  {
    "name": "No seller",
    "price": 500,
    "statistics": {
      "likes": 1,
      "viewCount": 1,
      "contacts": 1
    }
  }
  ```
- **Ожидаемый результат:**
  - `400 Bad Request`;
  - `status = "error"`, `result` — пустой объект либо описание ошибки валидации.
- **Фактический результат:** `400 Bad Request`, тело содержит `status` и `result`.  
- **Статус:** Passed.

#### TC-POST-03. `sellerID = 0`

- **Запрос:**
  ```json
  {
    "sellerID": 0,
    "name": "Zero seller",
    "price": 300,
    "statistics": {
      "likes": 1,
      "viewCount": 1,
      "contacts": 1
    }
  }
  ```
- **Ожидаемый результат:** `400 Bad Request` (идентификатор продавца не может быть 0).  
- **Фактический результат:** `400 Bad Request`.  
- **Статус:** Passed.

#### TC-POST-04. `sellerID < 0` (отрицательное значение)

- **Запрос:**
  ```json
  {
    "sellerID": -123456,
    "name": "Negative seller",
    "price": 400,
    "statistics": {
      "likes": 1,
      "viewCount": 1,
      "contacts": 1
    }
  }
  ```
- **Ожидаемый результат:** `400 Bad Request`.  
- **Фактический результат:** `200 OK`, `status = "ok"`.  
- **Статус:** Failed.

#### TC-POST-05. `sellerID` неверного типа (строка вместо числа)

- **Запрос:**
  ```json
  {
    "sellerID": "not-a-number",
    "name": "Bad type seller",
    "price": 400,
    "statistics": {
      "likes": 1,
      "viewCount": 1,
      "contacts": 1
    }
  }
  ```
- **Ожидаемый результат:** `400 Bad Request`.  
- **Фактический результат:** `400 Bad Request`.  
- **Статус:** Passed.

---

### 1.3. Негативные сценарии по `name`

#### TC-POST-06. Отсутствует обязательное поле `name`

- **Запрос:**
  ```json
  {
    "sellerID": 333333,
    "price": 700,
    "statistics": {
      "likes": 1,
      "viewCount": 1,
      "contacts": 1
    }
  }
  ```
- **Ожидаемый результат:** `400 Bad Request`.  
- **Фактический результат:** `400 Bad Request`.  
- **Статус:** Passed.

#### TC-POST-07. `name` — пустая строка

- **Запрос:**
  ```json
  {
    "sellerID": 333333,
    "name": "",
    "price": 700,
    "statistics": {
      "likes": 1,
      "viewCount": 1,
      "contacts": 1
    }
  }
  ```
- **Ожидаемый результат:** `400 Bad Request` (имя товара не может быть пустым).  
- **Фактический результат:** `400 Bad Request`.  
- **Статус:** Passed.

#### TC-POST-08. `name` неверного типа (число)

- **Запрос:**
  ```json
  {
    "sellerID": 333333,
    "name": 123,
    "price": 700,
    "statistics": {
      "likes": 1,
      "viewCount": 1,
      "contacts": 1
    }
  }
  ```
- **Ожидаемый результат:** `400 Bad Request`.  
- **Фактический результат:** `400 Bad Request`.  
- **Статус:** Passed.

---

### 1.4. Негативные сценарии по `price`

#### TC-POST-09. Отсутствует обязательное поле `price`

- **Запрос:**
  ```json
  {
    "sellerID": 444444,
    "name": "No price product",
    "statistics": {
      "likes": 1,
      "viewCount": 1,
      "contacts": 1
    }
  }
  ```
- **Ожидаемый результат:** `400 Bad Request`.  
- **Фактический результат:** `400 Bad Request`.  
- **Статус:** Passed.

#### TC-POST-10. `price < 0` (отрицательная цена)

- **Запрос:**
  ```json
  {
    "sellerID": 444444,
    "name": "Negative price",
    "price": -10,
    "statistics": {
      "likes": 1,
      "viewCount": 1,
      "contacts": 1
    }
  }
  ```
- **Ожидаемый результат:** `400 Bad Request`.  
- **Фактический результат:** `200 OK`, `status = "ok"`.  
- **Статус:** Failed.

#### TC-POST-11. `price` неверного типа (строка)

- **Запрос:**
  ```json
  {
    "sellerID": 444444,
    "name": "String price",
    "price": "100",
    "statistics": {
      "likes": 1,
      "viewCount": 1,
      "contacts": 1
    }
  }
  ```
- **Ожидаемый результат:** `400 Bad Request`.  
- **Фактический результат:** `400 Bad Request`.  
- **Статус:** Passed.

#### TC-POST-12. `price = 0`

- **Запрос:**
  ```json
  {
    "sellerID": 444444,
    "name": "Zero price",
    "price": 0,
    "statistics": {
      "likes": 1,
      "viewCount": 1,
      "contacts": 1
    }
  }
  ```
- **Ожидаемый результат:** `400 Bad Request` (цена должна быть > 0).  
- **Фактический результат:** `400 Bad Request`.  
- **Статус:** Passed.

---

### 1.5. Негативные сценарии по блоку `statistics`

#### TC-POST-13. Нет блока `statistics`

- **Запрос:**
  ```json
  {
    "sellerID": 555555,
    "name": "No stats",
    "price": 1000
  }
  ```
- **Ожидаемый результат:** `400 Bad Request`.  
- **Фактический результат:** `400 Bad Request`.  
- **Статус:** Passed.

#### TC-POST-14. Отсутствует поле `likes`

- **Запрос:**
  ```json
  {
    "sellerID": 555555,
    "name": "No likes",
    "price": 1000,
    "statistics": {
      "viewCount": 1,
      "contacts": 1
    }
  }
  ```
- **Ожидаемый результат:** `400 Bad Request`.  
- **Фактический результат:** `400 Bad Request`.  
- **Статус:** Passed.

#### TC-POST-15. `likes = 0`

- **Запрос:**
  ```json
  {
    "sellerID": 555555,
    "name": "Zero likes",
    "price": 1000,
    "statistics": {
      "likes": 0,
      "viewCount": 1,
      "contacts": 1
    }
  }
  ```
- **Ожидаемый результат:** `400 BadRequest` (лайков должно быть > 0).  
- **Фактический результат:** `400 Bad Request`.  
- **Статус:** Passed.

#### TC-POST-16. `likes < 0`

- **Запрос:**
  ```json
  {
    "sellerID": 555555,
    "name": "Negative likes",
    "price": 1000,
    "statistics": {
      "likes": -1,
      "viewCount": 1,
      "contacts": 1
    }
  }
  ```
- **Ожидаемый результат:** `400 Bad Request`.  
- **Фактический результат:** `200 OK`.  
- **Статус:** Failed.

#### TC-POST-17. Неверный тип поля `likes` (строка)

- **Запрос:**
  ```json
  {
    "sellerID": 555555,
    "name": "String likes",
    "price": 1000,
    "statistics": {
      "likes": "1",
      "viewCount": 1,
      "contacts": 1
    }
  }
  ```
- **Ожидаемый результат:** `400 Bad Request`.  
- **Фактический результат:** `400 Bad Request`.  
- **Статус:** Passed.

#### TC-POST-18. Нет поля `viewCount`

- **Запрос:**
  ```json
  {
    "sellerID": 555555,
    "name": "No viewCount",
    "price": 1000,
    "statistics": {
      "likes": 1,
      "contacts": 1
    }
  }
  ```
- **Ожидаемый результат:** `400 Bad Request`.  
- **Фактический результат:** `400 Bad Request`.  
- **Статус:** Passed.

#### TC-POST-19. `viewCount = 0`

- **Запрос:**
  ```json
  {
    "sellerID": 555555,
    "name": "Zero views",
    "price": 1000,
    "statistics": {
      "likes": 1,
      "viewCount": 0,
      "contacts": 1
    }
  }
  ```
- **Ожидаемый результат:** `400 Bad Request`.  
- **Фактический результат:** `400 Bad Request`.  
- **Статус:** Passed.

#### TC-POST-20. `viewCount < 0`

- **Запрос:**
  ```json
  {
    "sellerID": 555555,
    "name": "Negative views",
    "price": 1000,
    "statistics": {
      "likes": 1,
      "viewCount": -1,
      "contacts": 1
    }
  }
  ```
- **Ожидаемый результат:** `400 Bad Request`.  
- **Фактический результат:** `200 OK`.  
- **Статус:** Failed.

#### TC-POST-21. Неверный тип поля `viewCount` (строка)

- **Запрос:**
  ```json
  {
    "sellerID": 555555,
    "name": "String views",
    "price": 1000,
    "statistics": {
      "likes": 1,
      "viewCount": "1",
      "contacts": 1
    }
  }
  ```
- **Ожидаемый результат:** `400 Bad Request`.  
- **Фактический результат:** `400 Bad Request`.  
- **Статус:** Passed.

#### TC-POST-22. Нет поля `contacts`

- **Запрос:**
  ```json
  {
    "sellerID": 555555,
    "name": "No contacts",
    "price": 1000,
    "statistics": {
      "likes": 1,
      "viewCount": 1
    }
  }
  ```
- **Ожидаемый результат:** `400 Bad Request`.  
- **Фактический результат:** `400 Bad Request`.  
- **Статус:** Passed.

#### TC-POST-23. `contacts = 0`

- **Запрос:**
  ```json
  {
    "sellerID": 555555,
    "name": "Zero contacts",
    "price": 1000,
    "statistics": {
      "likes": 1,
      "viewCount": 1,
      "contacts": 0
    }
  }
  ```
- **Ожидаемый результат:** `400 Bad Request`.  
- **Фактический результат:** `400 Bad Request`.  
- **Статус:** Passed.

#### TC-POST-24. `contacts < 0`

- **Запрос:**
  ```json
  {
    "sellerID": 555555,
    "name": "Negative contacts",
    "price": 1000,
    "statistics": {
      "likes": 1,
      "viewCount": 1,
      "contacts": -1
    }
  }
  ```
- **Ожидаемый результат:** `400 Bad Request`.  
- **Фактический результат:** `200 OK`.  
- **Статус:** Failed.

#### TC-POST-25. Неверный тип поля `contacts` (строка)

- **Запрос:**
  ```json
  {
    "sellerID": 555555,
    "name": "String contacts",
    "price": 1000,
    "statistics": {
      "likes": 1,
      "viewCount": 1,
      "contacts": "1"
    }
  }
  ```
- **Ожидаемый результат:** `400 Bad Request`.  
- **Фактический результат:** `400 Bad Request`.  
- **Статус:** Passed.

---

## 2. GET /api/1/item/{id} — получение объявления по ID

> **Предусловие:** заранее создать минимум два объявления и сохранить их `itemId`. Одно из них удалить для проверки сценария с удалённым объектом.

#### TC-GET-ITEM-01. Получение существующего объявления

- **Запрос:**  
  `GET /api/1/item/{validItemId}`
- **Ожидаемый результат:**
  - `200 OK`;
  - в теле: `createdAt`, `id`, `name`, `price`, `sellerId`, `statistics`.
- **Фактический результат:** `200 OK`, все поля присутствуют.  
- **Статус:** Passed.

#### TC-GET-ITEM-02. Получение удалённого объявления

- **Запрос:**  
  `GET /api/1/item/{deletedItemId}`
- **Ожидаемый результат:** `404 Not Found`.  
- **Фактический результат:** `404 Not Found`.  
- **Статус:** Passed.

#### TC-GET-ITEM-03. Получение несуществующего объявления

- **Запрос:**  
  `GET /api/1/item/{randomNonExistingId}`
- **Ожидаемый результат:** `404 Not Found`.  
- **Фактический результат:** `404 Not Found`.  
- **Статус:** Passed.

#### TC-GET-ITEM-04. Невалидный формат `id` (не UUID)

- **Запрос:**  
  `GET /api/1/item/not-a-uuid`
- **Ожидаемый результат:** `400 Bad Request`.  
- **Фактический результат:** `400 Bad Request`.  
- **Статус:** Passed.

---

## 3. GET /api/1/statistic/{id} — получение статистики объявления

> **Предусловие:** использовать `itemId` ранее созданных объявлений (в том числе удалённых и несуществующих).

#### TC-GET-STAT-01. Статистика для валидного существующего объявления

- **Запрос:**  
  `GET /api/1/statistic/{validItemId}`
- **Ожидаемый результат:**
  - `200 OK`;
  - в теле: `contacts`, `likes`, `viewCount`.
- **Фактический результат:** `200 OK`, поля присутствуют.  
- **Статус:** Passed.

#### TC-GET-STAT-02. Статистика для удалённого объявления

- **Запрос:**  
  `GET /api/1/statistic/{deletedItemId}`
- **Ожидаемый результат:** `200 OK`, корректная статистика (в зависимости от требований).  
- **Фактический результат:** `200 OK`, структура ответа корректна.  
- **Статус:** Passed.

#### TC-GET-STAT-03. Статистика для несуществующего объявления

- **Запрос:**  
  `GET /api/1/statistic/{randomNonExistingId}`
- **Ожидаемый результат:** `404 Not Found`.  
- **Фактический результат:** `404 Not Found`.  
- **Статус:** Passed.

#### TC-GET-STAT-04. Невалидный формат `id`

- **Запрос:**  
  `GET /api/1/statistic/not-a-uuid`
- **Ожидаемый результат:** `400 Bad Request`.  
- **Фактический результат:** `400 Bad Request`.  
- **Статус:** Passed.

---

## 4. GET /api/1/{sellerID}/item — список объявлений продавца

#### TC-GET-SELLER-01. Список объявлений для существующего продавца

- **Запрос:**  
  `GET /api/1/222222/item`
- **Ожидаемый результат:**
  - `200 OK`;
  - в `result` — массив объектов с полями `createdAt`, `id`, `name`, `price`, `sellerId`, `statistics`.
- **Фактический результат:** `200 OK`, данные соответствуют ожиданиям.  
- **Статус:** Passed.

#### TC-GET-SELLER-02. Отрицательный `sellerID`

- **Запрос:**  
  `GET /api/1/-222222/item`
- **Ожидаемый результат:** `400 Bad Request`.  
- **Фактический результат:** `200 OK`, тело пустое.  
- **Статус:** Failed.

#### TC-GET-SELLER-03. Невалидный тип `sellerID` (буква)

- **Запрос:**  
  `GET /api/1/a/item`
- **Ожидаемый результат:** `400 Bad Request`.  
- **Фактический результат:** `400 Bad Request`.  
- **Статус:** Passed.

---

## 5. DELETE /api/2/item/{id} — удаление объявления

> **Предусловие:** создать несколько объявлений и сохранить их `itemId`.

#### TC-DEL-ITEM-01. Успешное удаление по валидному `id`

- **Запрос:**  
  `DELETE /api/2/item/{validItemId}`
- **Ожидаемый результат:** `200 OK`, тело пустое.  
- **Фактический результат:** `200 OK`, тело пустое.  
- **Статус:** Passed.

#### TC-DEL-ITEM-02. Повторное удаление того же объявления

- **Запрос:**  
  `DELETE /api/2/item/{alreadyDeletedItemId}`
- **Ожидаемый результат:** `404 Not Found`.  
- **Фактический результат:** `404 Not Found`.  
- **Статус:** Passed.

#### TC-DEL-ITEM-03. Удаление несуществующего объявления

- **Запрос:**  
  `DELETE /api/2/item/{randomNonExistingId}`
- **Ожидаемый результат:** `404 Not Found`.  
- **Фактический результат:** `404 Not Found`.  
- **Статус:** Passed.

#### TC-DEL-ITEM-04. Невалидный формат `id`

- **Запрос:**  
  `DELETE /api/2/item/not-a-uuid`
- **Ожидаемый результат:** `400 Bad Request`.  
- **Фактический результат:** `400 Bad Request`.  
- **Статус:** Passed.

---

> В API v2 также есть ручка `/api/2/statistic/{id}`.  
> По поведению она аналогична версии v1, поэтому в данном наборе покрыта только версия `/api/1/statistic/{id}`.
