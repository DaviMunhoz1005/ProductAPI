package com.api.product.controller;

import com.api.product.entities.Product;
import com.api.product.requests.ProductPostRequestBody;
import com.api.product.requests.ProductPutRequestbody;
import com.api.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /* Request Start API */
    @GetMapping(path = "start")
    public @ResponseBody ResponseEntity<String> start() {

        return new ResponseEntity<>("Welcome to products API!", HttpStatus.OK);
    }

    /* Request About API */
    @GetMapping(path = "about")
    public @ResponseBody ResponseEntity<String> about() {

        return new ResponseEntity<>("About the API...", HttpStatus.OK);
    }

    /* Request Contact API */
    @GetMapping(path = "contact")
    public @ResponseBody ResponseEntity<String> contact() {

        return new ResponseEntity<>("Contact us :)", HttpStatus.OK);
    }

    @GetMapping(path = "products")
    public @ResponseBody ResponseEntity<Page<Product>> findAllProducts(Pageable pageable) {

        return new ResponseEntity<>(productService.findAllProducts(pageable), HttpStatus.OK);
    }

    @PostMapping(path = "products")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductPostRequestBody product) {

        return new ResponseEntity<>(productService.addProduct(product), HttpStatus.CREATED);
    }

    @GetMapping(path = "products/{id}")
    public @ResponseBody ResponseEntity<Product> findById(@PathVariable Long id) {

        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "products/find")
    public @ResponseBody ResponseEntity<List<Product>> findByName(@RequestParam String name) {

        return new ResponseEntity<>(productService.findByName(name), HttpStatus.OK);
    }

    @PutMapping(path = "products/{id}")
    public ResponseEntity<Void> replaceProduct(@RequestBody ProductPutRequestbody product, @PathVariable Long id) {

        productService.replaceProduct(id, product);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
