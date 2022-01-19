package me.minikuma.example.service;

import lombok.RequiredArgsConstructor;
import me.minikuma.example.entity.Product;
import me.minikuma.example.repository.jpa.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // TODO: 상품 저장 (단건)
    @Transactional(readOnly = false)
    public Product saveProduct(final Product product) {
        return productRepository.save(product);
    }

    // TODO: 상품 저장 (다건)
    @Transactional(readOnly = false)
    public void saveProductList(final List<Product> products) {
        productRepository.saveAll(products);
    }

    // TODO: 상품 조건 (단건, ID 기준)
    public Product getProductById(final Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No Search Data"));
    }

    // TODO: 상품 조건 (다건, 멀티 Query 조건, name, price)
    public List<Product> getProductByConditions(final String name, final Integer price) {
        return productRepository.findByProductNameAndPriceGreaterThanEqual(name, price);
    }
}
