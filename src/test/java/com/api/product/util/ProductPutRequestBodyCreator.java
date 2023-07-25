package com.api.product.util;

import com.api.product.requests.ProductPutRequestbody;

public class ProductPutRequestBodyCreator {

    public static ProductPutRequestbody createProductPutRequestBody() {

        return ProductPutRequestbody.builder()
                .name(ProductCreator.createValidUpdatedProduct().getName())
                .value(ProductCreator.createValidUpdatedProduct().getValue())
                .quantity(ProductCreator.createValidUpdatedProduct().getQuantity())
                .build();
    }
}
