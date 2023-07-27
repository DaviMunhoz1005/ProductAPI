package com.api.product.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductPutRequestbody {

    @NotEmpty(message = "Enter a name for the product")
    @Schema(description = "This is the product name", example = "Stove")
    private String name;

    @DecimalMin(value = "1", message = "Please enter a valid value greater than 1")
    @Schema(description = "This is the product value", example = "5200.71")
    private double value;

    @DecimalMin(value = "0", message = "Please enter a valid quantity greater than 0")
    @Schema(description = "This is the product quantity", example = "45")
    private Integer quantity;
}
