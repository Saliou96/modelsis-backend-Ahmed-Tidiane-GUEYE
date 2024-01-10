package com.example.test.controller;

import com.example.test.Dto.ProductTypeDTO;
import com.example.test.Dto.ProductTypeRequestDTO;
import com.example.test.entity.ProductType;
import com.example.test.exceptions.RessourceNotFoundException;
import com.example.test.service.ProductTypeService;
import com.example.test.utils.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/productTypes")
public class ProductTypeController {
    private final ProductTypeService productTypeService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseData<Void>> createProductType(@RequestBody ProductTypeRequestDTO productTypeRequestDTO) {
        try {
            log.info("ProductType Creation");
            ProductType productType = modelMapper.map(productTypeRequestDTO, ProductType.class);
            productTypeService.createProductType(productType);
            ResponseData<Void> responseData = ResponseData.<Void>builder()
                    .code(String.valueOf(HttpStatus.CREATED.value()))
                    .message("ProductType created successfully")
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
        } catch (Exception e) {
            log.error("Error creating ProductType: {}", e.getMessage());
            ResponseData<Void> errorResponse = ResponseData.<Void>builder()
                    .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message("Failed to create ProductType")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<ProductTypeDTO>> getProductTypeById(@PathVariable Long id) {
        try {
            ProductType productType = productTypeService.getProductTypeById(id);
            ProductTypeDTO productTypeDTO = modelMapper.map(productType, ProductTypeDTO.class);

            ResponseData<ProductTypeDTO> responseData = ResponseData.<ProductTypeDTO>builder()
                    .code(String.valueOf(HttpStatus.OK.value()))
                    .data(productTypeDTO)
                    .build();

            return ResponseEntity.ok().body(responseData);
        } catch (RessourceNotFoundException e) {
            log.error("ProductType not found with ID: {}", id);
            ResponseData<ProductTypeDTO> errorResponse = ResponseData.<ProductTypeDTO>builder()
                    .code(String.valueOf(HttpStatus.NOT_FOUND.value()))
                    .message("Complexe not found with ID: " + id)
                    .build();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            log.error("Error retrieving ProductType with ID {}: {}", id, e.getMessage());
            ResponseData<ProductTypeDTO> errorResponse = ResponseData.<ProductTypeDTO>builder()
                    .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message("Failed to retrieve ProductType")
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseData<List<ProductTypeDTO>>> getAllProductType() {
        try {
            List<ProductTypeDTO> productTypeDTOS = productTypeService.getAllProductType().stream().map(productType -> modelMapper.map(productType, ProductTypeDTO.class)).collect(Collectors.toList());
            ResponseData<List<ProductTypeDTO>> responseData = ResponseData.<List<ProductTypeDTO>>builder()
                    .code(String.valueOf(HttpStatus.OK.value()))
                    .data(productTypeDTOS)
                    .build();
            return ResponseEntity.ok().body(responseData);
        } catch (Exception e) {
            log.error("Error retrieving all product Type: {}", e.getMessage());
            ResponseData<List<ProductTypeDTO>> errorResponse = ResponseData.<List<ProductTypeDTO>>builder()
                    .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message("Failed to retrieve product Type")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseData<ProductType>> updateProductType(@PathVariable Long id, @RequestBody ProductTypeDTO productTypeDTO) {
        try {
            ProductType productType = modelMapper.map(productTypeDTO, ProductType.class);
            ProductType updatedProductType = productTypeService.updateProductType(id, productType);
            ResponseData<ProductType> responseData = ResponseData.<ProductType>builder()
                    .code(String.valueOf(HttpStatus.OK.value()))
                    .data(updatedProductType)
                    .message("ProductType updated successfully")
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(responseData);
        } catch (Exception e) {
            log.error("Error updating ProductType with ID {}: {}", id, e.getMessage());
            ResponseData<ProductType> errorResponse = ResponseData.<ProductType>builder()
                    .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message("Failed to update complexe")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseData<Void>> deleteProductType(@PathVariable Long id) {
        try {
            productTypeService.deleteProductType(id);
            ResponseData<Void> responseData = ResponseData.<Void>builder()
                    .code(String.valueOf(HttpStatus.NO_CONTENT.value()))
                    .message("ProductType deleted successfully")
                    .build();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseData);
        } catch (Exception e) {
            log.error("Error deleting ProductType with ID {}: {}", id, e.getMessage());
            ResponseData<Void> errorResponse = ResponseData.<Void>builder()
                    .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message("Failed to delete ProductType")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
