package com.api.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record ProductDTO(
        @NotEmpty(message = "Enter a name for the product")
        @Schema(description = "This is the product name", example = "Refrigerator")
        String name,

        @DecimalMin(value = "0.10", message = "Please enter a valid value greater than 0.10")
        @Schema(description = "This is the product value", example = "1700.25")
        double value,

        @DecimalMin(value = "0", message = "Please enter a valid quantity greater than 0")
        @Schema(description = "This is the product quantity", example = "54")
        int quantity
) {

}