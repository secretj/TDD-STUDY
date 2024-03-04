package com.example.productorderservice.product;

import com.example.productorderservice.ApiTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.example.productorderservice.product.ProductSteps.상품등록_요청;
import static com.example.productorderservice.product.ProductSteps.상품요청;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProductApiTest extends ApiTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    void 상품등록() {
        final var request = 상품등록_요청();

        final var response = 상품요청(request);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    public void 상품조회(){
        ProductSteps.상품요청(ProductSteps.상품등록_요청());
        final Long productId = 1L;

        ExtractableResponse<Response> response = ProductSteps.상품조회_요청(productId);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 상품수정() {
        ProductSteps.상품요청(ProductSteps.상품등록_요청());
        final long productId = 1L;

        final var response = ProductSteps.상품수정_요청(productId);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(productRepository.findById(1L).get().getName()).isEqualTo("상품 수정");
    }
}

