package me.minikuma.example.common.dto;

import lombok.*;

public class ProductRequestDto {

    @Builder @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Save {
        private String productName;
        private String productDescription;
        private int price;
        private int quantity;
    }

    @Builder @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update {
        private String productName;
        private String productDescription;
        private int price;
        private int quantity;
    }
}
