package com.api.product.service;

import com.api.product.entities.Product;
import com.api.product.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {

        this.productRepository = productRepository;
    }

    /* List of products */
    public List<Product> findAllProducts() {

        return productRepository.findAll();
    }

    /* Add products */
    public Product addProduct(Product product) {

        return productRepository.save(product);
    }

    /* Find products by code */
    public Product findById(Long id) {

        return productRepository.findById(id).get();
    }

    /* Modify product */
    public Product modifyProduct(Long id, Product product) {

        Product currentProduct = productRepository.findById(id).get();
        BeanUtils.copyProperties(product, currentProduct, "id");
        return productRepository.save(currentProduct);
    }

    /* Delete product */
    public void deleteProduct(Long id) {

        Product product = findById(id);
        productRepository.delete(product);
    }
}
