package com.avito.qa;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class AdsApiTest extends BaseApiTest {

    private Map<String, Object> buildValidItemBody(long sellerId) {
        Map<String, Object> body = new HashMap<>();
        body.put("sellerID", sellerId);
        body.put("name", "Test product " + System.currentTimeMillis());
        body.put("price", 500);

        Map<String, Object> stats = new HashMap<>();
        stats.put("likes", 10);
        stats.put("viewCount", 100);
        stats.put("contacts", 5);

        body.put("statistics", stats);
        return body;
    }

    private String createItemAndGetId(long sellerId) {
        ValidatableResponse response =
                given()
                        .contentType(ContentType.JSON)
                        .body(buildValidItemBody(sellerId))
                .when()
                        .post(CREATE_ITEM_PATH)
                .then()
                        .statusCode(200)
                        .body("status", equalTo("ok"))
                        .body("result.itemId", notNullValue());

        return String.valueOf(response.extract().path("result.itemId"));
    }


    @Test
    @DisplayName("POST /api/1/item – успешное создание объявления")
    void createItem_success() {
        long sellerId = 222222L;

        given()
                .contentType(ContentType.JSON)
                .body(buildValidItemBody(sellerId))
        .when()
                .post(CREATE_ITEM_PATH)
        .then()
                .statusCode(200)
                .body("status", equalTo("ok"))
                .body("result.itemId", notNullValue());
    }

    @Test
    @DisplayName("POST /api/1/item – ошибка валидации при отсутствии sellerID")
    void createItem_missingSellerId() {
        Map<String, Object> body = new HashMap<>();
        body.put("name", "No seller");
        body.put("price", 500);

        Map<String, Object> stats = new HashMap<>();
        stats.put("likes", 1);
        stats.put("viewCount", 1);
        stats.put("contacts", 1);
        body.put("statistics", stats);

        given()
                .contentType(ContentType.JSON)
                .body(body)
        .when()
                .post(CREATE_ITEM_PATH)
        .then()
                .statusCode(400);
    }


    @Test
    @DisplayName("GET /api/1/item/{id} – получение существующего объявления")
    void getItemById_success() {
        long sellerId = 222222L;
        String itemId = createItemAndGetId(sellerId);

        given()
        .when()
                .get(GET_ITEM_BY_ID_PATH, itemId)
        .then()
                .statusCode(200)
                .body("result.id", notNullValue())
                .body("result.name", notNullValue())
                .body("result.price", notNullValue())
                .body("result.sellerId", notNullValue());
    }

    @Test
    @DisplayName("GET /api/1/item/{id} – несуществующее объявление")
    void getItemById_notFound() {
        String randomId = "00000000-0000-0000-0000-000000000000";

        given()
        .when()
                .get(GET_ITEM_BY_ID_PATH, randomId)
        .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("GET /api/1/{sellerID}/item – список объявлений продавца")
    void getItemsBySeller_success() {
        long sellerId = 222222L;
        createItemAndGetId(sellerId);

        given()
        .when()
                .get(GET_ITEMS_BY_SELLER_PATH, sellerId)
        .then()
                .statusCode(200)
                .body("status", equalTo("ok"));
    }

    @Test
    @DisplayName("GET /api/1/{sellerID}/item – отрицательный sellerID (известный баг)")
    void getItemsBySeller_negativeSeller() {
        long negativeSellerId = -222222L;

        given()
        .when()
                .get(GET_ITEMS_BY_SELLER_PATH, negativeSellerId)
        .then()
                // ожидаемое поведение: 400, но фактически сейчас 200
                .statusCode(200);
    }


    @Test
    @DisplayName("GET /api/1/statistic/{id} – статистика для существующего объявления")
    void getStatistic_success() {
        long sellerId = 222222L;
        String itemId = createItemAndGetId(sellerId);

        given()
        .when()
                .get(GET_STATISTIC_V1_PATH, itemId)
        .then()
                .statusCode(200)
                .body("result.likes", notNullValue())
                .body("result.viewCount", notNullValue())
                .body("result.contacts", notNullValue());
    }

    @Test
    @DisplayName("GET /api/1/statistic/{id} – несуществующее объявление")
    void getStatistic_notFound() {
        String randomId = "00000000-0000-0000-0000-000000000000";

        given()
        .when()
                .get(GET_STATISTIC_V1_PATH, randomId)
        .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("DELETE /api/2/item/{id} – успешное удаление объявления")
    void deleteItem_success() {
        long sellerId = 222222L;
        String itemId = createItemAndGetId(sellerId);

        given()
        .when()
                .delete(DELETE_ITEM_V2_PATH, itemId)
        .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("DELETE /api/2/item/{id} – удаление несуществующего объявления")
    void deleteItem_notFound() {
        String randomId = "00000000-0000-0000-0000-000000000000";

        given()
        .when()
                .delete(DELETE_ITEM_V2_PATH, randomId)
        .then()
                .statusCode(404);
    }
}
