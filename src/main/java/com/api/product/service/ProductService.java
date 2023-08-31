package com.api.product.service;

import com.api.product.entities.Product;
import com.api.product.exception.BadRequestException;
import com.api.product.repository.ProductRepository;
import com.api.product.dto.ProductDTO;

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
    public Product addProduct(ProductDTO productDTO) {

        Product product = Product.builder()
                .name(productDTO.name())
                .value(productDTO.value())
                .quantity(productDTO.quantity())
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
    public void replaceProduct(Long id, ProductDTO productDTO) {

        Product savedProduct = findById(id);
        if((!productDTO.name().isEmpty()) && (productDTO.value() != 0) && (productDTO.quantity() != 0)){

            Product product = Product.builder()
                    .name(productDTO.name())
                    .value(productDTO.value())
                    .quantity(productDTO.quantity())
                    .build();

            BeanUtils.copyProperties(product, savedProduct, "id");
            productRepository.save(savedProduct);
        }else if((productDTO.name().isEmpty()) && (productDTO.value() != 0) && (productDTO.quantity() != 0)) {

            Product product = Product.builder()
                    .name(savedProduct.getName())
                    .value(productDTO.value())
                    .quantity(productDTO.quantity())
                    .build();

            BeanUtils.copyProperties(product, savedProduct, "id");
            productRepository.save(savedProduct);
        }else if((!productDTO.name().isEmpty()) && (productDTO.value() == 0) && (productDTO.quantity() != 0)) {

            Product product = Product.builder()
                    .name(productDTO.name())
                    .value(savedProduct.getValue())
                    .quantity(productDTO.quantity())
                    .build();

            BeanUtils.copyProperties(product, savedProduct, "id");
            productRepository.save(savedProduct);
        }else if((!productDTO.name().isEmpty()) && (productDTO.value() == 0) && (productDTO.quantity() == 0)) {

            Product product = Product.builder()
                    .name(productDTO.name())
                    .value(savedProduct.getValue())
                    .quantity(savedProduct.getQuantity())
                    .build();

            BeanUtils.copyProperties(product, savedProduct, "id");
            productRepository.save(savedProduct);
        }else if((!productDTO.name().isEmpty()) && (productDTO.value() != 0) && (productDTO.quantity() == 0)) {

            Product product = Product.builder()
                    .name(productDTO.name())
                    .value(productDTO.value())
                    .quantity(savedProduct.getQuantity())
                    .build();

            BeanUtils.copyProperties(product, savedProduct, "id");
            productRepository.save(savedProduct);
        }else if((productDTO.name().isEmpty()) && (productDTO.value() == 0) && (productDTO.quantity() == 0)) {

            Product product = Product.builder()
                    .name(savedProduct.getName())
                    .value(savedProduct.getValue())
                    .quantity(savedProduct.getQuantity())
                    .build();

            BeanUtils.copyProperties(product, savedProduct, "id");
            productRepository.save(savedProduct);
        }
    }
    
    /* Delete product */
    @Transactional
    public void deleteProduct(Long id) {

        Product product = findById(id);
        productRepository.delete(product);
    }
}
