package com.api.product.requests;

import lombok.Data;

@Data
public class ProductPostRequestBody {

    private String name;
    private double value;
    private Integer quantity;
}
