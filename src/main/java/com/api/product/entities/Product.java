package com.api.product.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Product")
@Builder
public class Product extends RepresentationModel<Product> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_product")
    @JsonProperty("name")
    @NotEmpty(message = "Enter a name for the product")
    private String name;

    @Column(name = "value_product")
    @JsonProperty("value")
    @DecimalMin(value = "0.10", message = "Please enter a valid value greater than 0.20")
    private double value;

    @Column(name = "quantity_product")
    @JsonProperty("quantity")
    @DecimalMin(value = "0", message = "Please enter a valid quantity greater than 0")
    private Integer quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Double.compare(product.value, value) == 0 && Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(quantity, product.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value, quantity);
    }
}
