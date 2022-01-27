package me.minikuma.example.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("productId")
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCache {
    @Id
    private Long ProductId;
    private String productName;
    private String productDescription;
    private Integer price;
    private Integer quantity;
}
