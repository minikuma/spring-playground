package me.minikuma.example.controller;

import lombok.RequiredArgsConstructor;
import me.minikuma.example.common.dto.BaseResponse;
import me.minikuma.example.common.dto.ProductDto;
import me.minikuma.example.entity.Product;
import me.minikuma.example.entity.ProductCache;
import me.minikuma.example.repository.cache.ProductCacheRepository;
import me.minikuma.example.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;
    private final ProductCacheRepository productCacheRepository;
    private final MessageSourceAccessor messageSource;
    private final ModelMapper modelMapper;

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
    public ResponseEntity<?> insertProduct(@RequestBody ProductDto request) {
        Product product = modelMapper.map(request, Product.class);
        Product savedProduct = productService.saveProduct(product);
        Product findProduct = productService.getProductById(product.getProductId());

        URI uri = MvcUriComponentsBuilder.fromController(this.getClass())
                .path("/{productId}")
                .buildAndExpand(savedProduct.getProductId())
                .toUri();

        BaseResponse response = createSuccessBaseResponse(findProduct, HttpStatus.CREATED, "0000", false);

        return ResponseEntity.created(uri).body(response);
    }

    // TODO: 상품 저장 (다건)
    @PostMapping("/list")
    public ResponseEntity<?> saveProductList(@RequestBody List<ProductDto> request) throws IOException {
        // List<ProductDto> -> List<Product> 변환
        List<Product> products = request.stream()
                .map(r -> modelMapper.map(r, Product.class))
                .collect(Collectors.toList());

        productService.saveProductList(products);

        WeakHashMap<String, String> result = new WeakHashMap<>();
        result.put("resultMessage", messageSource.getMessage("complete"));

        return ResponseEntity.ok(result);
    }

    // TODO: 상품 조건 (단건, ID 기준)
    @GetMapping("/{productId}")
    public ResponseEntity<?> findProduct(@PathVariable("productId") Long productId) {
        // Redis Key 값 확인 이후 없는 경우 Database 접근
        Optional<ProductCache> cachedProduct = productCacheRepository.findById(productId);

        BaseResponse response;

        if (cachedProduct.isPresent()) {
            ProductCache productCache = cachedProduct.get();
            response = createSuccessBaseResponse(productCache, HttpStatus.OK, "0001", true);
        } else {
            Product findProduct = productService.getProductById(productId);
            // Product -> ProductCache 변환
            ProductCache convertCachedProduct = modelMapper.map(findProduct, ProductCache.class);

            productCacheRepository.save(convertCachedProduct); // cache save
            response = createSuccessBaseResponse(findProduct, HttpStatus.OK, "0000", false);
        }
        return ResponseEntity.ok(response);
    }

    // TODO: 상품 조건 (다건, 멀티 Query 조건, name, price)
    @GetMapping
    public ResponseEntity<?> findProductList(
            @RequestParam("productName") String productName,
            @RequestParam("price") int price) {
        List<Product> productList = productService.getProductByConditions(productName, price);

        // TODO: productList -> BaseResponse Object 변환
        BaseResponse response = createSuccessBaseResponse(productList, HttpStatus.OK, "0000", false);
        return ResponseEntity.ok(response);
    }

    // TODO: Private Method, Result Code ENUM 으로 변경, 파라미터 갯수가 너무 많음
    private <T> BaseResponse createSuccessBaseResponse (T data, HttpStatus httpStatus, String resultCode, Boolean isCached) {
        return BaseResponse.builder()
                .apiVersion("v1")
                .statusCode(httpStatus)
                .resultCode(resultCode)
                .resultMessage(messageSource.getMessage("complete"))
                .payload(data)
                .isCached(isCached)
                .build();
    }
}
