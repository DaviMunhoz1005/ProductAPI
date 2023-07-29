package com.api.product.util;

import com.api.product.requests.ProductRequestBody;

public class ProductRequestBodyCreator {

    public static ProductRequestBody createProductRequestBody() {

        return ProductRequestBody.builder()
                .name(ProductCreator.createProductToBeSaved().getName())
                .value(ProductCreator.createProductToBeSaved().getValue())
                .quantity(ProductCreator.createProductToBeSaved().getQuantity())
                .build();
    }
}
