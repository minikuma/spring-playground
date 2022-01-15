package me.minikuma.example.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Product Entity
 */

@Getter
@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(length = 100, nullable = false)
    private String productName;
    @Column(length = 500, nullable = false)
    private String productDescription;
    private int price;
    private int quantity;

    @Builder
    public Product(String productName, String productDescription, int price, int quantity) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
        this.quantity = quantity;
    }
}
