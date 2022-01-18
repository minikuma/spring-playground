package me.minikuma.example.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash("productId")
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCache {
    @Id
    private Long ProductId;
    private String productName;
    private String productDescription;
    private int price;
    private int quantity;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
