package com.example.productorderservice;

import org.springframework.stereotype.Component;

@Component
class ProductAdapter implements ProductPort {
    private ProductRepository productRepository;

    ProductAdapter(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void save(final Product product) {
        productRepository.save(product);
    }
}
