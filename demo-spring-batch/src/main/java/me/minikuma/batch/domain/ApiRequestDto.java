package me.minikuma.batch.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiRequestDto {
    private Long id;
    private ProductDto productDto;
}
