package com.example.productorderservice.product;

import com.example.productorderservice.DiscountPolicy;

import com.example.productorderservice.GetProductResponse;
import com.example.productorderservice.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ProductServiceTest
{
    private ProductService productService;
    @Test
    void 상품조회() {
        //상품등록
        productService.addProduct(ProductSteps.상품등록요청_생성());
        final long productId = 1L;
        //상품을 조회
        final GetProductResponse response = productService.getProduct(productId);

        //상품의 응답을 검증 - 검증을 가장 먼저 구현하는 것을 추천함
        assertThat(response).isNotNull();
    }
}
