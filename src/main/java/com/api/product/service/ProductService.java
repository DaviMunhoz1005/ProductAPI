package com.api.product.service;

import com.api.product.entities.ProductEntity;
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
    public List<ProductEntity> findAllProducts() {

        return productRepository.findAll();
    }

    /* Add products */
    public ProductEntity addProduct(ProductEntity product) {

        return productRepository.save(product);
    }

    /* Find products by code */
    public ProductEntity findById(Long id) {

        return productRepository.findById(id).get();
    }

    /* Modify product */
    public ProductEntity modifyProduct(ProductEntity product) {

        return productRepository.save(product);
    }

    /* Delete product */
    public void deleteProduct(Long id) {

        ProductEntity product = findById(id);
        productRepository.delete(product);
    }
}
