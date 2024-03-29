package com.example.productorderservice.product;

import org.springframework.util.Assert;

record GetProductResponse(Long id, String name, int price, DiscountPolicy disCountPolicy) {
    public GetProductResponse {
        Assert.notNull(id, "상품 ID는 필수입니다.");
        Assert.hasText(name, "상품명은 필수입니다.");
        Assert.notNull(disCountPolicy, "할인 정책은 필수 입니다.");
    }
}