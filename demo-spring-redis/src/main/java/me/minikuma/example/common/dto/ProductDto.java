package me.minikuma.example.common.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long productId;
    private String productName;
    private String productDescription;
    private Integer price;
    private Integer quantity;
}