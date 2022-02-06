package me.minikuma.example.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDto implements Serializable {
    private Long productId;
    private String productName;
    private String productDescription;
    private Integer price;
    private Integer quantity;
}