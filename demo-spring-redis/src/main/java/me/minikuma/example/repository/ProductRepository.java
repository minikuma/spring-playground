package me.minikuma.example.repository;

import me.minikuma.example.common.dto.ProductRequestDto;
import me.minikuma.example.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductNameAndPriceGreaterThanEqual(String name, int price);
}
