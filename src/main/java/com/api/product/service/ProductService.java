package com.api.product.service;

import com.api.product.entities.Product;
import com.api.product.exception.BadRequestException;
import com.api.product.repository.ProductRepository;
import com.api.product.requests.ProductPostRequestBody;
import com.api.product.requests.ProductPutRequestbody;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.BeanUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    /* List all of products */
    public Page<Product> listAllProductsPageable(Pageable pageable) {

        return productRepository.findAll(pageable);
    }

    public List<Product> listAllProducts() {

        return productRepository.findAll();
    }

    /* Add products */
    @Transactional
    public Product addProduct(ProductPostRequestBody productPostRequestBody) {

        Product product = Product.builder()
                .name(productPostRequestBody.getName())
                .value(productPostRequestBody.getValue())
                .quantity(productPostRequestBody.getQuantity())
                .build();
        return productRepository.save(product);
    }

    /* Find products by id */
    public Product findById(Long id) {

        return productRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Product not found"));
    }

    public List<Product> findByName(String name) {

        return productRepository.findByName(name);
    }

    /* Replace product */
    @Transactional
    public void replaceProduct(Long id, ProductPutRequestbody productPutRequestbody) {

        Product savedProduct = findById(id);
        Product product = Product.builder()
                .name(productPutRequestbody.getName())
                .value(productPutRequestbody.getValue())
                .quantity(productPutRequestbody.getQuantity())
                .build();
        BeanUtils.copyProperties(product, savedProduct, "id");
        productRepository.save(savedProduct);
    }

    /* Delete product */
    @Transactional
    public void deleteProduct(Long id) {

        Product product = findById(id);
        productRepository.delete(product);
    }
}
