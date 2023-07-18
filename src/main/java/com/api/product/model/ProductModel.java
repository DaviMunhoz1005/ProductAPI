package com.api.product.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Product")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_product")
    private int code_product;

    @Column(name = "name_product")
    private String name_product;

    @Column(name = "value_product")
    private double value_product;

    public int getCode_product() {
        return code_product;
    }

    public void setCode_product(int code_product) {
        this.code_product = code_product;
    }

    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public double getValue_product() {
        return value_product;
    }

    public void setValue_product(double value_product) {
        this.value_product = value_product;
    }
}
