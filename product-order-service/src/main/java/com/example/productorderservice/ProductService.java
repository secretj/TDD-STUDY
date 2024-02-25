package com.example.productorderservice;

import org.springframework.stereotype.Service;

@Service
class ProductService {
    private final ProductPort productPort;

    ProductService(final ProductPort productPort) {
        this.productPort = productPort;
    }

    public void addProduct(final AddProductRequest request) {
        final Product product = new Product(request.name(), request.price(), request.discountPolicy());

        productPort.save(product);
    }
}
