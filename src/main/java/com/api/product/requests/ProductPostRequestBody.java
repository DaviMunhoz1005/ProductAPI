package com.api.product.requests;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ProductPostRequestBody {

    @NotEmpty(message = "Enter a name for the product")
    private String name;

    @DecimalMin(value = "1", message = "Please enter a valid value greater than 1")
    private double value;

    @DecimalMin(value = "0", message = "Please enter a valid quantity greater than 0")
    private Integer quantity;
}
