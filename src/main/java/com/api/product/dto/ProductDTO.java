package com.api.product.dto;

import com.api.product.entities.Product;
import org.springframework.beans.BeanUtils;

/* Data Transfer Object */

public class ProductDTO {

    private Long id;

    private String nameProduct;

    private double valueProduct;

    private Integer quantityProduct;

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
}
