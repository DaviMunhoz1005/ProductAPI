package com.api.product.dto;

import com.api.product.entities.Product;
import org.springframework.beans.BeanUtils;

/* Data Transfer Object */

public class ProductDTO {

    private Long id;

    private String name;

    private double value;

    private Integer quantity;

    public ProductDTO() {
    }

    public ProductDTO(Product entity) {

        BeanUtils.copyProperties(entity, this);
    }

    /* Get Set */

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public double getValue() {

        return value;
    }

    public void setValue(double value) {

        this.value = value;
    }

    public Integer getQuantity() {

        return quantity;
    }

    public void setQuantity(Integer quantity) {

        this.quantity = quantity;
    }
}
