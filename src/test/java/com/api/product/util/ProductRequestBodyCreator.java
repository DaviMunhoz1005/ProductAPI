package com.api.product.util;

import com.api.product.dto.ProductDTO;

public class ProductRequestBodyCreator {

    public static ProductDTO createProductDTO() {

        return ProductDTO.builder()
                .name(ProductCreator.createProductToBeSaved().getName())
                .value(ProductCreator.createProductToBeSaved().getValue())
                .quantity(ProductCreator.createProductToBeSaved().getQuantity())
                .build();
    }
}
