package me.minikuma.example.controller;

import lombok.RequiredArgsConstructor;
import me.minikuma.example.common.dto.BaseResponse;
import me.minikuma.example.common.dto.ProductRequestDto;
import me.minikuma.example.entity.Product;
import me.minikuma.example.service.ProductService;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<?> options() {
        //@formatter:off
        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.HEAD)
                .build();
        //@formatter:on
    }

    // TODO: 상품 저장 (단건) Controller
    @PostMapping("/save")
    public ResponseEntity<?> insertProduct(@RequestBody ProductRequestDto.Save request) {
        Product savedProduct = productService.saveProduct(request);

        URI uri = MvcUriComponentsBuilder.fromController(this.getClass())
                .path("/{productId}")
                .buildAndExpand(savedProduct.getProductId())
                .toUri();

        // TODO: savedProduct -> BaseResponse Object 변환
        BaseResponse response = BaseResponse.builder()
                .apiVersion("V1")
                .statusCode(HttpStatus.CREATED.value())
                .resultCode("0000")
                .resultMessage("정상 등록되었습니다.")
                .payload(savedProduct)
                .build();

        return ResponseEntity.created(uri).body(response);
    }

    // TODO: 상품 저장 (다건)

    // TODO: 상품 조건 (단건, ID 기준)

    // TODO: 상품 조건 (다건, 멀티 Query 조건, name, price)
}
