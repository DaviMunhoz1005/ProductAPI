package com.api.product.controller;

import com.api.product.entities.Product;

import com.api.product.dto.ProductDTO;

import com.api.product.service.ProductService;

import com.api.product.util.ProductCreator;
import com.api.product.util.ProductRequestBodyCreator;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productServiceMock;

    @BeforeEach
    void setUp() {

        PageImpl<Product> productPage = new PageImpl<>(List.of(ProductCreator.createValidProduct()));
        BDDMockito.when(productServiceMock.listAllProductsPageable(ArgumentMatchers.any()))
                .thenReturn(productPage);

        BDDMockito.when(productServiceMock.listAllProducts())
                .thenReturn(List.of(ProductCreator.createValidProduct()));

        BDDMockito.when(productServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(ProductCreator.createValidProduct());

        BDDMockito.when(productServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(ProductCreator.createValidProduct()));

        BDDMockito.when(productServiceMock.addProduct(ArgumentMatchers.any(ProductDTO.class)))
                .thenReturn(ProductCreator.createValidProduct());

        BDDMockito.doNothing().when(productServiceMock).replaceProduct(ArgumentMatchers.anyLong(),
                ArgumentMatchers.any(ProductDTO.class));

        BDDMockito.doNothing().when(productServiceMock).deleteProduct(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("list return list of products inside page object when successful")
    void list_ReturnListOfProductsInsidePageObject_WhenSuccessful() {

        String expectedName = ProductCreator.createValidProduct().getName();
        Page<Product> productPage = productController.listAllProductsPageable(null).getBody();

        Assertions.assertThat(productPage).isNotNull();
        Assertions.assertThat(productPage.toList()).isNotEmpty().hasSize(1);

        Assertions.assertThat(productPage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("list return list of products when successful")
    void list_ReturnListOfProducts_WhenSuccessful() {

        String expectedName = ProductCreator.createValidProduct().getName();
        List<Product> products = productController.listAllProducts().getBody();

        Assertions.assertThat(products).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(products.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("find by id return product when successful")
    void findById_ReturnProduct_WhenSuccessful() {

        Long expectedId = ProductCreator.createValidProduct().getId();
        Product product = productController.findById(1L).getBody();

        Assertions.assertThat(product).isNotNull();
        Assertions.assertThat(product.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("find by name return a list of product when successful")
    void findByName_ReturnListOfProduct_WhenSuccessful() {

        String expectedName = ProductCreator.createValidProduct().getName();
        List<Product> product = productController.findByName("Arroz").getBody();

        Assertions.assertThat(product).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(product.get(0).getName()).isNotEmpty().isEqualTo(expectedName);
    }

    @Test
    @DisplayName("find by name return an empty list of product when product is not found")
    void findByName_ReturnAnEmptyListOfProduct_WhenProductIsNotFound() {

        BDDMockito.when(productServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Product> product = productController.findByName("Arroz").getBody();

        Assertions.assertThat(product).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("add product return product when successful")
    void addProduct_ReturnProduct_WhenSuccessful() {

        Product product = productController.addProduct(ProductRequestBodyCreator.createProductDTO())
                .getBody();

        Assertions.assertThat(product).isNotNull().isEqualTo(ProductCreator.createValidProduct());
    }

    @Test
    @DisplayName("replace updates product when successful")
    void replace_UpdatesProduct_WhenSuccessful() {

        Long expectedId = ProductCreator.createValidUpdatedProduct().getId();
        ResponseEntity<Void> product = productController.replaceProduct(
                ProductRequestBodyCreator.createProductDTO(), expectedId);

        Assertions.assertThat(product).isNotNull();
        Assertions.assertThat(product.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes product when successful")
    void delete_RemovesProduct_WhenSuccessful() {

        Assertions.assertThatCode(() -> productController.deleteProduct(1L))
                .doesNotThrowAnyException();

        ResponseEntity<Void> product = productController.deleteProduct(1L);

        Assertions.assertThat(product).isNotNull();
        Assertions.assertThat(product.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}