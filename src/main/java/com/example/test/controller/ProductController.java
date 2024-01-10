package com.example.test.controller;

import com.example.test.Dto.ProductRequestDTO;
import com.example.test.entity.Product;
import com.example.test.entity.ProductType;
import com.example.test.exceptions.RessourceNotFoundException;
import com.example.test.service.ProductService;
import com.example.test.service.ProductTypeService;
import com.example.test.utils.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;
import com.example.test.Dto.ProductDTO;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ProductTypeService productTypeService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ResponseData<Void>> createNewProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        try {
            Product product = modelMapper.map(productRequestDTO, Product.class);
            ProductType productType = productTypeService.getProductTypeById(productRequestDTO.getProductTypeId());
            if (productType != null) {
                product.setProductType(productType);
                productService.createProduct(product);
                ResponseData<Void> responseData = ResponseData.<Void>builder()
                        .code(String.valueOf(HttpStatus.CREATED.value()))
                        .message("Product created successfully")
                        .build();
                return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
            } else {
                ResponseData<Void> errorResponse = ResponseData.<Void>builder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .message("Invalid Product type ID.")
                        .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
        } catch (Exception e) {
            log.error("Error creating new Product: {}", e.getMessage());
            ResponseData<Void> errorResponse = ResponseData.<Void>builder()
                    .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message("Failed to create new Product")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @GetMapping
    public ResponseEntity<ResponseData<List<ProductDTO>>> getAllProduct() {
        try {
            List<ProductDTO> productDTOS = productService.getAllProduct()
                    .stream()
                    .map(product -> modelMapper.map(product, ProductDTO.class))
                    .collect(Collectors.toList());
            ResponseData<List<ProductDTO>> responseData = ResponseData.<List<ProductDTO>>builder()
                    .code(String.valueOf(HttpStatus.OK.value()))
                    .data(productDTOS)
                    .build();
            return ResponseEntity.ok().body(responseData);
        } catch (Exception e) {
            log.error("Error retrieving all Product: {}", e.getMessage());
            ResponseData<List<ProductDTO>> errorResponse = ResponseData.<List<ProductDTO>>builder()
                    .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message("Failed to retrieve Product")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<ProductDTO>> getProductdById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
            productDTO.setProductTypeId(product.getProductType().getId());
            ResponseData<ProductDTO> responseData = ResponseData.<ProductDTO>builder()
                    .code(String.valueOf(HttpStatus.OK.value()))
                    .data(productDTO)
                    .build();
            return ResponseEntity.ok().body(responseData);
        } catch (RessourceNotFoundException e) {
            log.error("Product not found with ID: {}", id);
            ResponseData<ProductDTO> errorResponse = ResponseData.<ProductDTO>builder()
                    .code(String.valueOf(HttpStatus.NOT_FOUND.value()))
                    .message("Product not found with ID: " + id)
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            log.error("Error retrieving Product with ID {}: {}", id, e.getMessage());
            ResponseData<ProductDTO> errorResponse = ResponseData.<ProductDTO>builder()
                    .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message("Failed to retrieve Product")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseData<Product>> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        try {
            Product product = modelMapper.map(productDTO, Product.class);
            Product updatedProduct = productService.updateProduct(id, product);
            ResponseData<Product> responseData = ResponseData.<Product>builder()
                    .code(String.valueOf(HttpStatus.OK.value()))
                    .data(updatedProduct)
                    .message("Product updated successfully")
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(responseData);
        } catch (Exception e) {
            log.error("Error updating Product with ID {}: {}", id, e.getMessage());
            ResponseData<Product> errorResponse = ResponseData.<Product>builder()
                    .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message("Failed to update Product")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseData<Void>> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            ResponseData<Void> responseData = ResponseData.<Void>builder()
                    .code(String.valueOf(HttpStatus.NO_CONTENT.value()))
                    .message("Product deleted successfully")
                    .build();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseData);
        } catch (Exception e) {
            log.error("Error deleting Product with ID {}: {}", id, e.getMessage());
            ResponseData<Void> errorResponse = ResponseData.<Void>builder()
                    .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message("Failed to delete product")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
