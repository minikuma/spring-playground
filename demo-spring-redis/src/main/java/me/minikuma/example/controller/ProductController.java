package me.minikuma.example.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.minikuma.example.common.dto.BaseResponse;
import me.minikuma.example.common.dto.ProductRequestDto;
import me.minikuma.example.entity.Product;
import me.minikuma.example.service.ProductService;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.DataInput;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.WeakHashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;
    private final MessageSourceAccessor messageSource;
    private final ObjectMapper objectMapper;

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
    public ResponseEntity<?> insertProduct(@RequestBody ProductRequestDto.Save request) throws IOException {
        Product product = objectMapper.convertValue(request, Product.class);
        Product savedProduct = productService.saveProduct(product);
        URI uri = MvcUriComponentsBuilder.fromController(this.getClass())
                .path("/{productId}")
                .buildAndExpand(savedProduct.getProductId())
                .toUri();

        // TODO: savedProduct -> BaseResponse Object 변환
        BaseResponse response = BaseResponse.builder()
                .apiVersion("V1")
                .statusCode(HttpStatus.CREATED.value())
                .resultCode("0000")
                .resultMessage(messageSource.getMessage("complete"))
                .payload(savedProduct)
                .build();

        return ResponseEntity.created(uri).body(response);
    }

    // TODO: 상품 저장 (다건)
    @PostMapping("/list")
    public ResponseEntity<?> saveProductList(@RequestBody List<ProductRequestDto.Save> request) throws IOException {
        List<Product> products = objectMapper.convertValue(request, new TypeReference<List<Product>>() {});
        productService.saveProductList(products);
        WeakHashMap<String, String> result = new WeakHashMap<>();
        result.put("resultMessage", messageSource.getMessage("complete"));
        return ResponseEntity.ok(result);
    }

    // TODO: 상품 조건 (단건, ID 기준)
    @GetMapping("/{productId}")
    public ResponseEntity<?> findProduct(@PathVariable("productId") Long productId) {
        Product findProduct = productService.getProductById(productId);

        // TODO: findProduct -> BaseResponse Object 변환
        BaseResponse response = BaseResponse.builder()
                .apiVersion("V1")
                .statusCode(HttpStatus.CREATED.value())
                .resultCode("0000")
                .resultMessage(messageSource.getMessage("complete"))
                .payload(findProduct)
                .build();
        return ResponseEntity.ok(response);
    }

    // TODO: 상품 조건 (다건, 멀티 Query 조건, name, price)
    @GetMapping
    public ResponseEntity<?> findProductList(
            @RequestParam("productName") String productName,
            @RequestParam("price") int price) {
        List<Product> productList = productService.getProductByConditions(productName, price);

        // TODO: productList -> BaseResponse Object 변환
        BaseResponse response = BaseResponse.builder()
                .apiVersion("V1")
                .statusCode(HttpStatus.CREATED.value())
                .resultCode("0000")
                .resultMessage(messageSource.getMessage("complete"))
                .payload(productList)
                .build();
        return ResponseEntity.ok(response);
    }
}
