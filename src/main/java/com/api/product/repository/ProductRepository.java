package com.api.product.repository;

import com.api.product.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductModel, Integer> {

    //List all products
    public List<ProductModel> findAll();

    //Find product by code
    public ProductModel findByCode(int code_product);

    //Delete product
    public void delete(ProductModel product);

    //Save product
    public ProductModel save(ProductModel product);
}
