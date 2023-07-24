package com.api.product.service;

import com.api.product.dto.ProductDTO;
import com.api.product.entities.Product;
import com.api.product.exception.BadRequestException;
import com.api.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    /* List all of products */
    public List<ProductDTO> findAllProducts() {

        List<Product> result = productRepository.findAll();
        return result.stream().map(ProductDTO::new).toList();
    }

    /* Add products */
    @Transactional
    public Product addProduct(Product product) {

        return productRepository.save(product);
    }

    /* Find products by code */
    public ProductDTO findByIdDto(Long id) {

        Product result = productRepository.findById(id)
                                          .orElseThrow(() -> new BadRequestException("Product not find"));
        return new ProductDTO(result);
    }

    public List<Product> findByName(String name) {

        return productRepository.findByName(name);
    }

    /* Replace product */
    @Transactional
    public Product replaceProduct(Long id, Product product) {

        Product currentProduct = productRepository.findById(id)
                                                  .orElseThrow(() -> new BadRequestException("Product to replace was not found"));
        BeanUtils.copyProperties(product, currentProduct, "id");
        return productRepository.save(currentProduct);
    }

    public Product findById(Long id) {

        return productRepository.findById(id)
                                .orElseThrow(() -> new BadRequestException("Product to delete was not found"));
    }

    /* Delete product */
    @Transactional
    public void deleteProduct(Long id) {

        Product product = findById(id);
        productRepository.delete(product);
    }
}
