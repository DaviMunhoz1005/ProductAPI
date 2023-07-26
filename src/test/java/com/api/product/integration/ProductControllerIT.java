package com.api.product.integration;

import com.api.product.entities.Product;
import com.api.product.entities.User;
import com.api.product.repository.ProductRepository;
import com.api.product.repository.UserRepository;
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

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
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
    @Qualifier(value = "testRestTemplateRoleUser")
    private TestRestTemplate testRestTemplateRoleUser;

    @Autowired
    @Qualifier(value = "testRestTemplateRoleAdmin")
    private TestRestTemplate testRestTemplateRoleAdmin;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    private static final User USER = User.builder()
            .name("Davidin")
            .password("{bcrypt}$2a$10$JZybZyZ3eYEDsFuhAuxameLp4pj/pHB06PRHeHqQnYPzaP9DWHpVO")
            .username("davi")
            .authorities("ROLE_USER")
            .build();

    private static final User ADMIN = User.builder()
            .name("Davidin")
            .password("{bcrypt}$2a$10$JZybZyZ3eYEDsFuhAuxameLp4pj/pHB06PRHeHqQnYPzaP9DWHpVO")
            .username("davi")
            .authorities("ROLE_ADMIN")
            .build();

    @TestConfiguration
    @Lazy
    static class Config {

        @Bean(name = "testRestTemplateRoleUser")
        public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port) {

            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .basicAuthentication("teste2", "1005");

            return new TestRestTemplate(restTemplateBuilder);
        }

        @Bean(name = "testRestTemplateRoleAdmin")
        public TestRestTemplate testRestTemplateRoleAdminCreator(@Value("${local.server.port}") int port) {

            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .basicAuthentication("davi", "1005");

            return new TestRestTemplate(restTemplateBuilder);
        }
    }

    @Test
    @DisplayName("listAll returns a pageable list of products when successful")
    void listAll_ReturnListOfProductsInsidePageObject_WhenSuccessful() {

        userRepository.save(USER);

        Product savedProduct = productRepository.save(ProductCreator.createValidProduct());
        String expectedName = savedProduct.getName();

        Page<Product> animePage = testRestTemplateRoleUser.exchange("/api/products", HttpMethod.GET, null ,
                new ParameterizedTypeReference<PageableResponse<Product>>() {
                }).getBody();

        Assertions.assertThat(animePage).isNotNull();

        Assertions.assertThat(animePage.toList()).isNotEmpty();

        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("list return list of products when successful")
    void list_ReturnListOfProducts_WhenSuccessful() {

        userRepository.save(USER);

        Product savedProduct = productRepository.save(ProductCreator.createValidProduct());
        String expectedName = savedProduct.getName();

        List<Product> products = testRestTemplateRoleUser.exchange("/api/products/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Product>>() {
                }).getBody();

        Assertions.assertThat(products).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(products.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("find by id return product when successful")
    void findById_ReturnProduct_WhenSuccessful() {

        userRepository.save(USER);

        Product savedProduct = productRepository.save(ProductCreator.createValidProduct());
        Long expectedId = savedProduct.getId();
        Product product = testRestTemplateRoleUser.getForObject("/api/products/{id}", Product.class, expectedId);

        Assertions.assertThat(product).isNotNull();
        Assertions.assertThat(product.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("find by name return a list of product when successful")
    void findByName_ReturnListOfProduct_WhenSuccessful() {

        userRepository.save(USER);

        Product savedProduct = productRepository.save(ProductCreator.createValidProduct());
        String expectedName = savedProduct.getName();

        String url = String.format("/api/products/find?name=%s", expectedName);
        List<Product> products = testRestTemplateRoleUser.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Product>>() {
                }).getBody();

        Assertions.assertThat(products).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(products.get(0).getName()).isNotEmpty().isEqualTo(expectedName);
    }

    @Test
    @DisplayName("find by name return an empty list of product when product is not found")
    void findByName_ReturnAnEmptyListOfProduct_WhenProductIsNotFound() {

        userRepository.save(USER);

        Product savedProduct = productRepository.save(ProductCreator.createValidProduct());

        List<Product> products = testRestTemplateRoleUser.exchange("/api/products/find?name=Feijão", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Product>>() {
                }).getBody();

        Assertions.assertThat(products).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("add product return product when successful")
    void addProduct_ReturnProduct_WhenSuccessful() {

        userRepository.save(ADMIN);

        ProductPostRequestBody productPostRequestBody = ProductPostRequestBodyCreator.createProductPostRequestBody();
        ResponseEntity<Product> productResponseEntity = testRestTemplateRoleAdmin.postForEntity("/api/products/admin",
                productPostRequestBody, Product.class);

        Assertions.assertThat(productResponseEntity).isNotNull();
        Assertions.assertThat(productResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(productResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(productResponseEntity.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("replace updates product when successful")
    void replace_UpdatesProduct_WhenSuccessful() {

        userRepository.save(USER);

        Product savedProduct = productRepository.save(ProductCreator.createValidProduct());
        savedProduct.setName("Batata");
        savedProduct.setValue(10);
        savedProduct.setQuantity(3);

        Long expectedId = savedProduct.getId();

        ProductPutRequestbody productPutRequestBody = ProductPutRequestBodyCreator.createProductPutRequestBody();
        ResponseEntity<Void> productResponseEntity = testRestTemplateRoleUser.exchange("/api/products/{id}",
                HttpMethod.PUT, new HttpEntity<>(savedProduct), Void.class, expectedId);

        Assertions.assertThat(productResponseEntity).isNotNull();
        Assertions.assertThat(productResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes product when successful")
    void delete_RemovesProduct_WhenSuccessful() {

        userRepository.save(ADMIN);

        Product savedProduct = productRepository.save(ProductCreator.createValidProduct());

        Long expectedId = savedProduct.getId();

        ResponseEntity<Void> productResponseEntity = testRestTemplateRoleAdmin.exchange("/api/products/admin/{id}",
                HttpMethod.DELETE, null, Void.class, expectedId);

        Assertions.assertThat(productResponseEntity).isNotNull();
        Assertions.assertThat(productResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete returns 403 when user is not admin")
    void delete_Returns403_WhenUserIsNotAdmin() {

        userRepository.save(USER);

        Product savedProduct = productRepository.save(ProductCreator.createValidProduct());

        Long expectedId = savedProduct.getId();

        ResponseEntity<Void> productResponseEntity = testRestTemplateRoleUser.exchange("/api/products/admin/{id}",
                HttpMethod.DELETE, null, Void.class, expectedId);

        Assertions.assertThat(productResponseEntity).isNotNull();
        Assertions.assertThat(productResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
