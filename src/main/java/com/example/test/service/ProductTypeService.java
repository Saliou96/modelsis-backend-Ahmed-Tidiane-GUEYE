package com.example.test.service;

import com.example.test.entity.ProductType;
import com.example.test.exceptions.RessourceNotFoundException;
import com.example.test.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductTypeService {

    @Autowired
    private ProductTypeRepository productTypeRepository;

    public ProductType createProductType(ProductType productType) {
        return this.productTypeRepository.save(productType);
    }

    public ProductType updateProductType(Long id, ProductType productType) {
        ProductType existingpProductType = productTypeRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException(HttpStatus.NOT_FOUND, "ProductType not found with ID: " + id));
        existingpProductType.setProductTypeName(productType.getProductTypeName());
        return productTypeRepository.save(existingpProductType);
    }

    public ProductType getProductTypeById(Long id) {
        Optional<ProductType> productType = productTypeRepository.findById(id);
        return productType.orElseThrow(() -> new RessourceNotFoundException(HttpStatus.NOT_FOUND, "ProductType not found with ID: " + id));
    }

    public List<ProductType> getAllProductType() {
        return productTypeRepository.findAll();
    }

    public void deleteProductType(Long id) {
        if (!productTypeRepository.existsById(id)) {
            throw new RessourceNotFoundException(HttpStatus.NOT_FOUND, "ProductType not found with ID: " + id);
        }
        this.productTypeRepository.deleteById(id);
    }
}
