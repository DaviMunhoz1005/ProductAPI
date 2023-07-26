package com.api.product.repository;

import com.api.product.entities.Product;
import com.api.product.util.ProductCreator;

import jakarta.validation.ConstraintViolationException;

import lombok.extern.log4j.Log4j2;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Test for Products Repository")
@Log4j2
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Save persists product when successful")
    void save_PesrsistProduct_WhenSuccessful() {

        Product productToBeSaved = ProductCreator.createProductToBeSaved();
        Product productSaved = this.productRepository.save(productToBeSaved);

        Assertions.assertThat(productSaved).isNotNull();
        Assertions.assertThat(productSaved.getId()).isNotNull();
        Assertions.assertThat(productSaved.getName()).isEqualTo(productToBeSaved.getName());
        Assertions.assertThat(productSaved.getValue()).isEqualTo(productToBeSaved.getValue());
        Assertions.assertThat(productSaved.getQuantity()).isEqualTo(productToBeSaved.getQuantity());
    }

    @Test
    @DisplayName("Save updates product when successful")
    void save_UpdatesProduct_WhenSuccessful() {

        Product productToBeSaved = ProductCreator.createProductToBeSaved();
        Product productSaved = this.productRepository.save(productToBeSaved);
        productSaved.setName("Linguiça");
        Product productUpdated = this.productRepository.save(productSaved);

        log.info(productUpdated.getName());

        Assertions.assertThat(productUpdated).isNotNull();
        Assertions.assertThat(productUpdated.getId()).isNotNull();
        Assertions.assertThat(productUpdated.getName()).isEqualTo(productSaved.getName());
        Assertions.assertThat(productUpdated.getValue()).isEqualTo(productSaved.getValue());
        Assertions.assertThat(productUpdated.getQuantity()).isEqualTo(productSaved.getQuantity());
    }

    @Test
    @DisplayName("Delete removes product when successful")
    void delete_RemovesProduct_WhenSuccessful() {

        Product productToBeSaved = ProductCreator.createProductToBeSaved();
        Product productSaved = this.productRepository.save(productToBeSaved);

        this.productRepository.delete(productSaved);
        Optional<Product> productOptional = this.productRepository.findById(productSaved.getId());

        Assertions.assertThat(productOptional).isEmpty();
    }

    @Test
    @DisplayName("Find by name returns list of product when successful")
    void findByName_ReturnsListOfProduct_WhenSuccessful() {

        Product productToBeSaved = ProductCreator.createProductToBeSaved();
        Product productSaved = this.productRepository.save(productToBeSaved);

        String name = productSaved.getName();
        List<Product> products = this.productRepository.findByName(name);

        Assertions.assertThat(products).isNotEmpty()
                .contains(productSaved);
    }

    @Test
    @DisplayName("Find by name returns empty list of product when no product is found")
    void findByName_ReturnsEmptyListOfProduct_WhenProductIsNotFound() {

        List<Product> products = this.productRepository.findByName("testName");
        Assertions.assertThat(products).isEmpty();
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name us empty")
    void save_ThrowConstraintViolationException_WhenNameIsEmpty() {

        Product product = new Product();
        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.productRepository.save(product))
                .withMessageContaining("Enter a name for the product");
    }


}