package com.api.product.service;


import com.api.product.entities.Product;
import com.api.product.exception.BadRequestException;
import com.api.product.repository.ProductRepository;
import com.api.product.util.ProductCreator;
import com.api.product.util.ProductPostRequestBodyCreator;
import com.api.product.util.ProductPutRequestBodyCreator;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepositoryMock;

    @BeforeEach
    void setUp() {

        PageImpl<Product> productPage = new PageImpl<>(List.of(ProductCreator.createValidProduct()));
        BDDMockito.when(productRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(productPage);

        BDDMockito.when(productRepositoryMock.findAll())
                .thenReturn(List.of(ProductCreator.createValidProduct()));

        BDDMockito.when(productRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(ProductCreator.createValidProduct()));

        BDDMockito.when(productRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(ProductCreator.createValidProduct()));

        BDDMockito.when(productRepositoryMock.save(ArgumentMatchers.any(Product.class)))
                .thenReturn(ProductCreator.createValidProduct());

        BDDMockito.doNothing().when(productRepositoryMock).delete(ArgumentMatchers.any(Product.class));
    }

    @Test
    @DisplayName("list return list of products inside page object when successful")
    void list_ReturnListOfProductsInsidePageObject_WhenSuccessful() {

        String expectedName = ProductCreator.createValidProduct().getName();
        Page<Product> productPage = productService.listAllProductsPageable(PageRequest.of(1, 1));

        Assertions.assertThat(productPage).isNotNull();
        Assertions.assertThat(productPage.toList()).isNotEmpty().hasSize(1);

        Assertions.assertThat(productPage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("list return list of products when successful")
    void list_ReturnListOfProducts_WhenSuccessful() {

        String expectedName = ProductCreator.createValidProduct().getName();
        List<Product> products = productService.listAllProducts();

        Assertions.assertThat(products).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(products.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("find by id return product when successful")
    void findById_ReturnProduct_WhenSuccessful() {

        Long expectedId = ProductCreator.createValidProduct().getId();
        Product product = productService.findById(1L);

        Assertions.assertThat(product).isNotNull();
        Assertions.assertThat(product.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("find by id throws Bad Request Exception when product is not found")
    void findById_ThrowsBadRequestException_WhenProductIsNotFound() {

        BDDMockito.when(productRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> this.productService.findById(1L));
    }

    @Test
    @DisplayName("find by name return a list of product when successful")
    void findByName_ReturnListOfProduct_WhenSuccessful() {

        String expectedName = ProductCreator.createValidProduct().getName();
        List<Product> product = productService.findByName("Arroz");

        Assertions.assertThat(product).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(product.get(0).getName()).isNotEmpty().isEqualTo(expectedName);
    }

    @Test
    @DisplayName("find by name return an empty list of product when product is not found")
    void findByName_ReturnAnEmptyListOfProduct_WhenProductIsNotFound() {

        BDDMockito.when(productRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Product> product = productService.findByName("Arroz");

        Assertions.assertThat(product).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("add product return product when successful")
    void addProduct_ReturnProduct_WhenSuccessful() {

        Product product = productService.addProduct(ProductPostRequestBodyCreator.createProductPostRequestBody());

        Assertions.assertThat(product).isNotNull().isEqualTo(ProductCreator.createValidProduct());
    }

    @Test
    @DisplayName("replace updates product when successful")
    void replace_UpdatesProduct_WhenSuccessful() {

        Assertions.assertThatCode(() ->productService.replaceProduct(1L,
                ProductPutRequestBodyCreator.createProductPutRequestBody()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("delete removes product when successful")
    void delete_RemovesProduct_WhenSuccessful() {

        Assertions.assertThatCode(() -> productService.deleteProduct(1L))
                .doesNotThrowAnyException();
    }

}