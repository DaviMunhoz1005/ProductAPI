package com.api.product.util;

import com.api.product.requests.ProductPostRequestBody;

public class ProductPostRequestBodyCreator {

    public static ProductPostRequestBody createProductPostRequestBody() {

        return ProductPostRequestBody.builder()
                .name(ProductCreator.createProductToBeSaved().getName())
                .value(ProductCreator.createProductToBeSaved().getValue())
                .quantity(ProductCreator.createProductToBeSaved().getQuantity())
                .build();
    }
}
