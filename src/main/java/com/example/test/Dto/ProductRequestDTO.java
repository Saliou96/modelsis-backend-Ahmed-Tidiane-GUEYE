package com.example.test.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductRequestDTO {
    private String productName;
    private Long productTypeId;
    private Date dateCreated;
}
