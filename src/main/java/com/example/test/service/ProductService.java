package com.example.test.service;

import com.example.test.entity.Product;
import com.example.test.entity.ProductType;
import com.example.test.exceptions.RessourceNotFoundException;
import com.example.test.repository.ProductRepository;
import com.example.test.repository.ProductTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductTypeRepository productTypeRepository;

    public Product createProduct(Product product) {
        return this.productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Product not found with ID: " + id));
        existingProduct.setProductName(product.getProductName());
        existingProduct.setProductType(product.getProductType());
        return this.productRepository.save(existingProduct);
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException(HttpStatus.NOT_FOUND, "Product not found with ID: " + id));
    }


    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
