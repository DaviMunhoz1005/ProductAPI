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

    public Page<Product> listAllProductsPageable(Pageable pageable) {

        return productRepository.findAll(pageable);
    }

    public List<Product> listAllProducts() {


        return productRepository.findAll();
    }

    @Transactional
    public Product addProduct(ProductDTO productDTO) {

        Product product = Product.builder()
                .name(productDTO.name())
                .value(productDTO.value())
                .quantity(productDTO.quantity())
                .build();
        return productRepository.save(product);
    }

    public Product findById(Long id) {

        return productRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Product not found!"));
    }

    public List<Product> findByName(String name) {

        return productRepository.findByName(name);
    }

    @Transactional
    public void replaceProduct(Long id, ProductDTO productDTO) {

        Product savedProduct = findById(id);

        Product product = Product.builder()
                .name(productDTO.name() != null ? productDTO.name() : savedProduct.getName())
                .value(productDTO.value() >= 0.10 ? productDTO.value() : savedProduct.getValue())
                .quantity(productDTO.quantity() >= 0 ? productDTO.quantity() : savedProduct.getQuantity())
                .build();

        BeanUtils.copyProperties(product, savedProduct, "id");
        productRepository.save(savedProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {

        productRepository.deleteById(id);
    }
}
