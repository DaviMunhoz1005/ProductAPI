package com.api.product.controller;

import com.api.product.model.ProductModel;
import com.api.product.service.ProductService;
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
    public @ResponseBody List<ProductModel> findAllProducts() {

        return productService.findAllProducts();
    }

    @PostMapping(path = "products/add")
    public ProductModel addProduct(@RequestBody ProductModel product) {

        return productService.addProduct(product);
    }

    @GetMapping(value = "products/{id}")
    public ProductModel findById(@PathVariable Long id) {

        return productService.findById(id);
    }

    @PutMapping(path = "products/modify")
    public @ResponseBody ProductModel modifyProduct(@RequestBody ProductModel product) {

        return productService.modifyProduct(product);
    }

    @DeleteMapping(path = "products/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);
    }
}
