package com.avito.qa;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseApiTest {

    protected static final String BASE_URL = "https://qa-internship.avito.com";

    protected static final String CREATE_ITEM_PATH = "/api/1/item";                   // POST
    protected static final String GET_ITEM_BY_ID_PATH = "/api/1/item/{itemId}";       // GET
    protected static final String GET_ITEMS_BY_SELLER_PATH = "/api/1/{sellerId}/item";// GET
    protected static final String GET_STATISTIC_V1_PATH = "/api/1/statistic/{itemId}";// GET
    protected static final String DELETE_ITEM_V2_PATH = "/api/2/item/{itemId}";       // DELETE

    @BeforeAll
    static void setupRestAssured() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}

