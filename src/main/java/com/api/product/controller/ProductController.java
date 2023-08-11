package com.api.product.controller;

import com.api.product.entities.Product;

import com.api.product.dto.ProductDTO;

import com.api.product.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Log4j2
public class ProductController {

    private final ProductService productService;

//    springDoc: localhost:8080/swagger-ui.html
//    springActuator: localhost:8080/actuator  [/health, /metrics, /info]

    @GetMapping(path = "products")
    @Operation(summary = "List all products in page form", description = "default page size is 20, use size parameter to change",
            tags = {"User and administrator have access"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation")
    })
    public @ResponseBody ResponseEntity<Page<Product>> listAllProductsPageable(@ParameterObject
                                                                               Pageable pageable) {

        Page<Product> productList = productService.listAllProductsPageable(pageable);
        if(productList.isEmpty()) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {

            for (Product product : productList) {

                long id = product.getId();
                product.add(linkTo(methodOn(ProductController.class).findById(id)).withRel("Product Description"));
            }
            return new ResponseEntity<>(productList, HttpStatus.OK);
        }
    }

    @GetMapping(path = "products/all")
    @Operation(summary = "List all registered products", tags = {"User and administrator have access"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "List of products not found")
    })
    public @ResponseBody ResponseEntity<List<Product>> listAllProducts() {

        List<Product> productList =  productService.listAllProducts();
        if(productList.isEmpty()) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {

            for (Product product : productList) {

                long id = product.getId();
                product.add(linkTo(methodOn(ProductController.class).findById(id)).withRel("Product Description"));
            }
            return new ResponseEntity<>(productList, HttpStatus.OK);
        }
    }

    @GetMapping(path = "products/{id}")
    @Operation(summary = "Redeem a product registered by its id", description = "Define the id of the product you want " +
            "to redeem in the id parameter",
            tags = {"User and administrator have access"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "Product not found in database")
    })
    public @ResponseBody ResponseEntity<Product> findById(@PathVariable Long id) {

       Product product = productService.findById(id);
       product.add(linkTo(methodOn(ProductController.class).listAllProducts()).withRel("List of products"));
       return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "products/admin/{id}")
    @Operation(summary = "Redeem a product by its id and user information", description = "Define the id of the product" +
            " you want to redeem in the id parameter",
            tags = {"Only administrators have access"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "Product not found in database"),
            @ApiResponse(responseCode = "401", description = "User does not have authorization to find the product " +
                    "along with user information")
    })
    public @ResponseBody ResponseEntity<Product> findByIdAuthentication(@PathVariable Long id,
                                                                        @AuthenticationPrincipal UserDetails userDetails) {

        log.info(userDetails);
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "products/find")
    @Operation(summary = "Redeem a product by its name", description = "Define the name of the product you want to redeem" +
            " in the name parameter",
            tags = {"User and administrator have access"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation")
    })
    public @ResponseBody ResponseEntity<List<Product>> findByName(@RequestParam String name) {

        return new ResponseEntity<>(productService.findByName(name), HttpStatus.OK);
    }

    @PostMapping(path = "products")
    @Operation(summary = "Create a product", description = "Set a name, value, and quantity to create a product",
            tags = {"User and administrator have access"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "If the name is empty or null"),
            @ApiResponse(responseCode = "400", description = "If the value is equal to or less than 0"),
            @ApiResponse(responseCode = "400", description = "If quantity is less than 0")
    })
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductDTO product) {

        return new ResponseEntity<>(productService.addProduct(product), HttpStatus.CREATED);
    }

    @PutMapping(path = "products/{id}")
    @Operation(summary = "Replace a product", description = "Set a name or value or quantity to create a product, " +
            "use the id parameter to identify the product you want to replace ",
            tags = {"User and administrator have access"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "Product not found in database")
    })
    public ResponseEntity<Void> replaceProduct(@RequestBody ProductDTO product, @PathVariable Long id) {

        productService.replaceProduct(id, product);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "products/admin/{id}")
    @Operation(summary = "Delete a product by its id", description = "Set the id of the product you want to delete in" +
            " the id parameter",
            tags = {"Only administrators have access"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "Product not found in database"),
            @ApiResponse(responseCode = "401", description = "User does not have authorization to delete the product")
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
