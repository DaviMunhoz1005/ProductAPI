package com.api.product.controller;

import com.api.product.dto.ProductDTO;
import com.api.product.entities.Product;
import com.api.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public @ResponseBody ResponseEntity<List<ProductDTO>> findAllProducts() {

        return new ResponseEntity<>(productService.findAllProducts(), HttpStatus.OK);
    }

    @PostMapping(path = "products")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {

        return new ResponseEntity<>(productService.addProduct(product), HttpStatus.CREATED);
    }

    @GetMapping(path = "products/{id}")
    public @ResponseBody ResponseEntity<ProductDTO> findById(@PathVariable Long id) {

        return new ResponseEntity<>(productService.findByIdDto(id), HttpStatus.OK);
    }

    @GetMapping(path = "products/find")
    public @ResponseBody ResponseEntity<List<Product>> findByName(@RequestParam String name) {

        return new ResponseEntity<>(productService.findByName(name), HttpStatus.OK);
    }

    @PutMapping(path = "products/{id}")
    public ResponseEntity<Product> replaceProduct(@RequestBody Product product, @PathVariable Long id) {

        return new ResponseEntity<>(productService.replaceProduct(id, product), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
