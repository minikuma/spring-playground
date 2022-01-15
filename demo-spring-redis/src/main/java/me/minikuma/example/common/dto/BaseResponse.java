package me.minikuma.example.common.dto;

import lombok.*;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {
    private String apiVersion;
    private int statusCode;
    private Object payload;
    private String resultCode;
    private String resultMessage;
}
