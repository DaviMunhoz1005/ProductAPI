package com.api.product.service;

import com.api.product.model.ProductModel;
import com.api.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {

        this.productRepository = productRepository;
    }

    /* List of products */
    public List<ProductModel> findAllProducts() {

        return productRepository.findAll();
    }

    /* Add products */
    public ProductModel addProduct(ProductModel product) {

        return productRepository.save(product);
    }

    /* Find products by code */
    public ProductModel findById(Long id) {

        return productRepository.findById(id).get();
    }

    /* Modify product */
    public ProductModel modifyProduct(ProductModel product) {

        return productRepository.save(product);
    }

    /* Delete product */
    public void deleteProduct(Long id) {

        ProductModel product = findById(id);
        productRepository.delete(product);
    }
}
