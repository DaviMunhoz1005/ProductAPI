package com.api.product.util;

import com.api.product.entities.Product;

public class ProductCreator {

    public static Product createProductToBeSaved() {

        return Product.builder()
                .name("Arroz")
                .value(10.5)
                .quantity(13)
                .build();
    }

    public static Product createValidProduct() {

        return Product.builder()
                .id(1L)
                .name("Arroz")
                .value(10.5)
                .quantity(13)
                .build();
    }

    public static Product createValidUpdatedProduct() {

        return Product.builder()
                .id(1L)
                .name("Arroz branco")
                .value(10.5)
                .quantity(13)
                .build();
    }
}
