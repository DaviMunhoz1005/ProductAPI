package com.api.product.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "Product")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_product")
    @JsonProperty("name")
    private String nameProduct;

    @Column(name = "value_product")
    @JsonProperty("value")
    private double valueProduct;

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
}
