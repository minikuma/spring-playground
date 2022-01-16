package me.minikuma.example.service;

import lombok.RequiredArgsConstructor;
import me.minikuma.example.common.dto.BaseResponse;
import me.minikuma.example.common.dto.ProductRequestDto;
import me.minikuma.example.entity.Product;
import me.minikuma.example.repository.ProductRepository;
import org.springframework.boot.web.server.Http2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // TODO: 상품 저장 (단건)
    public Product saveProduct(final ProductRequestDto.Save request) {

        // TODO: Save Request -> Product Object 로 변경
        Product product = Product.builder()
                .productName(request.getProductName())
                .productDescription(request.getProductDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();

        return productRepository.save(product);
    }

    // TODO: 상품 저장 (다건)
    public void saveProductList(List<Product> productList) {

    }

    // TODO: 상품 조건 (단건, ID 기준)
    public BaseResponse getProductById(Long id) {
        return new BaseResponse();
    }

    // TODO: 상품 조건 (다건, 멀티 Query 조건, name, price)
    public BaseResponse getProductByConditions(String name, int price) {
        return new BaseResponse();
    }
}
