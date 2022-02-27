package me.minikuma.batch.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity(name = "PRODUCT")
public class ProductEntity {
    @Id
    private Long id;
    private String name;
    private int price;
    private String type;
}
