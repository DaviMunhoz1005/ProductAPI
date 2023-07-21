package com.api.product.controller;

import com.api.product.entities.Product;
import com.api.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {

        this.productService = productService;
    }

    /* Request Start API */
    @GetMapping(path = "start")
    public @ResponseBody String start() {

        return "Welcome to products API!";
    }

    /* Request About API */
    @GetMapping(path = "about")
    public @ResponseBody String about() {

        return "About the API...";
    }

    /* Request Contact API */
    @GetMapping(path = "contact")
    public @ResponseBody String contact() {

        return "Contact us :)";
    }

    @GetMapping(path = "products")
    public @ResponseBody List<Product> findAllProducts() {

        return productService.findAllProducts();
    }

    @PostMapping(path = "products")
    public Product addProduct(@Valid @RequestBody Product product) {

        return productService.addProduct(product);
    }

    @GetMapping(value = "products/{id}")
    public @ResponseBody Product findById(@PathVariable Long id) {

        return productService.findById(id);
    }

    @PutMapping(path = "products/{id}")
    public Product modifyProduct(@RequestBody Product product, @PathVariable Long id) {

        return productService.modifyProduct(id, product);
    }

    @DeleteMapping(path = "products/{id}")
    public void deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);
    }
}
