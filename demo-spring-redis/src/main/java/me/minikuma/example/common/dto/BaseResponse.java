package me.minikuma.example.common.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {
    private String apiVersion;
    private HttpStatus statusCode;
    private Object payload;
    private String resultCode;
    private String resultMessage;
    private Boolean isCached;
}
