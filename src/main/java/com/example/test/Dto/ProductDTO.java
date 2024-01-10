package com.example.test.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductDTO {
    private Long id;
    private String productName;
    private Long productTypeId;
    private Date dateCreated;

    // Ajoutez d'autres champs si nécessaire, par exemple, description, prix, etc.

    // Constructeurs, getters et setters
}
