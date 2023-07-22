package com.api.product.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;


@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_product")
    @JsonProperty("name")
    @NotEmpty(message = "Enter a name for the product")
    private String nameProduct;

    @Column(name = "value_product")
    @JsonProperty("value")
    private double valueProduct;

    @Column(name = "quantity_product")
    @JsonProperty("quantity")
    private Integer quantityProduct;

    /* Get Set */

    public Long getId() {

        return id;
    }

    public void setId(long id) {

        this.id = id;
    }

    public String getNameProduct() {

        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {

        this.nameProduct = nameProduct;
    }

    public double getValueProduct() {

        return valueProduct;
    }

    public void setValueProduct(double valueProduct) {

        this.valueProduct = valueProduct;
    }

    public Integer getQuantityProduct() {

        return quantityProduct;
    }

    public void setQuantityProduct(Integer quantityProduct) {

        this.quantityProduct = quantityProduct;
    }

    /* Equals HashCode ToString*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(id, product.id) && Objects.equals(nameProduct, product.nameProduct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameProduct);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nameProduct='" + nameProduct + '\'' +
                ", valueProduct=" + valueProduct +
                ", quantityProduct=" + quantityProduct +
                '}';
    }
}
