package com.api.product.util;

import com.api.product.dto.ProductDTO;

public class ProductRequestBodyCreator {

    public static ProductDTO createProductDTO() {

        return ProductDTO.builder()
                .name(ProductCreator.createValidUpdatedProduct().getName())
                .value(ProductCreator.createValidUpdatedProduct().getValue())
                .quantity(ProductCreator.createValidUpdatedProduct().getQuantity())
                .build();
    }
}
