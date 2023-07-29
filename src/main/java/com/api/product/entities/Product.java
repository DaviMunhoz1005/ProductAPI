package com.api.product.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "Product")
@Builder
public class Product implements Serializable {

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
    @DecimalMin(value = "1", message = "Please enter a valid value greater than 1")
    private double value;

    @Column(name = "quantity_product")
    @JsonProperty("quantity")
    @DecimalMin(value = "0", message = "Please enter a valid quantity greater than 0")
    private Integer quantity;

    public Product() {
    }

    public Product(Long id, String name, double value, Integer quantity) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.quantity = quantity;
    }

    /* Equals HashCode ToString*/
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

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", nameProduct='" + name + '\''
                + ", valueProduct=" + value + ", quantityProduct=" + quantity + '}';
    }
}
