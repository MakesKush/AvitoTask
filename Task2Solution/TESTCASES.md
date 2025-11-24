1. POST /api/1/item — создание объявления
1.1. Позитивные сценарии
TC-POST-01. Успешное создание объявления с корректными данными

Предусловие: нет.

Запрос:

POST /api/1/item
Content-Type: application/json

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


Ожидаемый результат:

HTTP 200 OK;

status = "ok";

в result присутствует itemId и основные поля объявления.

Фактический результат: 200 OK, status = "ok", result.itemId возвращается.

Статус: ✅ Passed.

1.2. Негативные сценарии по sellerID
TC-POST-02. Отсутствует обязательное поле sellerID

Запрос:

{
  "name": "No seller",
  "price": 500,
  "statistics": {
    "likes": 1,
    "viewCount": 1,
    "contacts": 1
  }
}


Ожидаемый результат:

400 Bad Request;

status = "error", result — пустой объект либо описание ошибки валидации.

Фактический результат: 400 Bad Request, тело содержит status и result.

Статус: ✅ Passed.

TC-POST-03. sellerID = 0

Запрос:

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


Ожидаемый результат: 400 Bad Request (идентификатор продавца не может быть 0).

Фактический результат: 400 Bad Request.

Статус: ✅ Passed.

TC-POST-04. sellerID < 0 (отрицательное значение)

Запрос:

{
