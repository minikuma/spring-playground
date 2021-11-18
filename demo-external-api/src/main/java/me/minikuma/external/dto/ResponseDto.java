package me.minikuma.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    @JsonProperty("documents")
    private List<Documents> documents;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Documents {
        @JsonProperty("place_name")
        private String placeName;
    }
}
