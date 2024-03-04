package com.example.productorderservice.product;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class ProductSteps {
    public static ExtractableResponse<Response> 상품요청(AddProductRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/products")
                .then()
                .log().all().extract();
    }

    public static AddProductRequest 상품등록_요청() {
        final String name = "상품명";
        final int price = 1000;
        final DiscountPolicy disCountPolicy = DiscountPolicy.NONE;
        final AddProductRequest request = new AddProductRequest(name, price, disCountPolicy.NONE);
        return request;
    }

    final

    public static ExtractableResponse<Response> 상품조회_요청(Long productId) {
        return RestAssured.given().log().all()
                .when()
                .get("/products/{productId}" , productId)
                .then()
                .log().all()
                .extract();
    }

    public static UpdateProductRequest 상품수정_요청_생성() {
        return new UpdateProductRequest();
    }

    public static ExtractableResponse<Response> 상품수정_요청(final Long productId) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(상품수정_요청_생성())
                .when()
                .patch("/products/{productId}", productId)
                .then().log().all()
                .extract();
    }
}

