package me.minikuma.example.repository.cache;

import me.minikuma.example.entity.ProductCache;
import org.springframework.data.repository.CrudRepository;

public interface ProductCacheRepository extends CrudRepository<ProductCache, Long> {
}
