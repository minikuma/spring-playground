package me.minikuma.example.common.dto;

import lombok.*;

public class ProductRequestDto {
    @Getter
    @AllArgsConstructor
    @Builder
    public static class Save {
        private String productName;
        private String productDescription;
        private int price;
        private int quantity;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Update {
        private String productName;
        private String productDescription;
        private int price;
        private int quantity;
    }
}
