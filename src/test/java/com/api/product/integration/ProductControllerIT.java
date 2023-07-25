package com.api.product.integration;
import com.api.product.entities.Product;
import com.api.product.repository.ProductRepository;
import com.api.product.requests.ProductPostRequestBody;
import com.api.product.requests.ProductPutRequestbody;
import com.api.product.util.ProductCreator;
import com.api.product.util.ProductPostRequestBodyCreator;
import com.api.product.util.ProductPutRequestBodyCreator;
import com.api.product.wrapper.PageableResponse;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ProductRepository productRepository;

    @LocalServerPort
    private Integer port;

    @Test
    @DisplayName("listAll returns a pageable list of products when successful")
    void listAll_ReturnListOfProductsInsidePageObject_WhenSuccessful() {

        Product savedProduct = productRepository.save(ProductCreator.createValidProduct());
        String expectedName = savedProduct.getName();

        Page<Product> animePage = testRestTemplate.exchange("/api/products", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Product>>() {
                }).getBody();

        Assertions.assertThat(animePage).isNotNull();

        Assertions.assertThat(animePage.toList()).isNotEmpty();

        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("list return list of products when successful")
    void list_ReturnListOfProducts_WhenSuccessful() {

        Product savedProduct = productRepository.save(ProductCreator.createValidProduct());
        String expectedName = savedProduct.getName();

        List<Product> products = testRestTemplate.exchange("/api/products/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Product>>() {
                }).getBody();

        Assertions.assertThat(products).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(products.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("find by id return product when successful")
    void findById_ReturnProduct_WhenSuccessful() {

        Product savedProduct = productRepository.save(ProductCreator.createValidProduct());
        Long expectedId = savedProduct.getId();
        Product product = testRestTemplate.getForObject("/api/products/{id}", Product.class, expectedId);

        Assertions.assertThat(product).isNotNull();
        Assertions.assertThat(product.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("find by name return a list of product when successful")
    void findByName_ReturnListOfProduct_WhenSuccessful() {

        Product savedProduct = productRepository.save(ProductCreator.createValidProduct());
        String expectedName = savedProduct.getName();

        String url = String.format("/api/products/find?name=%s", expectedName);
        List<Product> products = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Product>>() {
                }).getBody();

        Assertions.assertThat(products).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(products.get(0).getName()).isNotEmpty().isEqualTo(expectedName);
    }

    @Test
    @DisplayName("find by name return an empty list of product when product is not found")
    void findByName_ReturnAnEmptyListOfProduct_WhenProductIsNotFound() {

        Product savedProduct = productRepository.save(ProductCreator.createValidProduct());

        List<Product> products = testRestTemplate.exchange("/api/products/find?name=Feijão", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Product>>() {
                }).getBody();

        Assertions.assertThat(products).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("add product return product when successful")
    void addProduct_ReturnProduct_WhenSuccessful() {

        ProductPostRequestBody productPostRequestBody = ProductPostRequestBodyCreator.createProductPostRequestBody();
        ResponseEntity<Product> productResponseEntity = testRestTemplate.postForEntity("/api/products", productPostRequestBody, Product.class);

        Assertions.assertThat(productResponseEntity).isNotNull();
        Assertions.assertThat(productResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(productResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(productResponseEntity.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("replace updates product when successful")
    void replace_UpdatesProduct_WhenSuccessful() {

        Product savedProduct = productRepository.save(ProductCreator.createValidProduct());
        savedProduct.setName("Batata");
        savedProduct.setValue(10);
        savedProduct.setQuantity(3);

        Long expectedId = savedProduct.getId();

        ProductPutRequestbody productPutRequestBody = ProductPutRequestBodyCreator.createProductPutRequestBody();
        ResponseEntity<Void> productResponseEntity = testRestTemplate.exchange("/api/products/{id}",
        HttpMethod.PUT, new HttpEntity<>(savedProduct), Void.class, expectedId);

        Assertions.assertThat(productResponseEntity).isNotNull();
        Assertions.assertThat(productResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes product when successful")
    void delete_RemovesProduct_WhenSuccessful() {

        Product savedProduct = productRepository.save(ProductCreator.createValidProduct());

        Long expectedId = savedProduct.getId();

        ResponseEntity<Void> productResponseEntity = testRestTemplate.exchange("/api/products/{id}",
                HttpMethod.DELETE, null, Void.class, expectedId);

        Assertions.assertThat(productResponseEntity).isNotNull();
        Assertions.assertThat(productResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
