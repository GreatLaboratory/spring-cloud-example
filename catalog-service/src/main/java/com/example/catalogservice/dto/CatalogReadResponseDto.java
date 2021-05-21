package com.example.catalogservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CatalogReadResponseDto {

    private String productId;
    private String productName;
    private Integer stock;
    private Integer unitPrice;
    private Date createdAt;

}
