package me.minikuma.example.service;

import lombok.RequiredArgsConstructor;
import me.minikuma.example.common.dto.BaseResponse;
import me.minikuma.example.common.dto.ProductRequestDto;
import me.minikuma.example.entity.Product;
import me.minikuma.example.repository.ProductRepository;
import org.springframework.boot.web.server.Http2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // TODO: 상품 저장 (단건)
    @Transactional(readOnly = false)
    public Product saveProduct(final Product requestProduct) {

        // TODO: Save Request -> Product Object 로 변경
        Product product = Product.builder()
                .productName(requestProduct.getProductName())
                .productDescription(requestProduct.getProductDescription())
                .price(requestProduct.getPrice())
                .quantity(requestProduct.getQuantity())
                .build();

        return productRepository.save(product);
    }

    // TODO: 상품 저장 (다건)
    public void saveProductList(final List<Product> products) {
        productRepository.saveAll(products);
    }

    // TODO: 상품 조건 (단건, ID 기준)
    public Product getProductById(final Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No Search Data"));
    }

    // TODO: 상품 조건 (다건, 멀티 Query 조건, name, price)
    public List<Product> getProductByConditions(final String name, final int price) {
        return productRepository.findByProductNameAndPriceGreaterThanEqual(name, price);
    }
}
