package me.minikuma.external.dto.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchQueryDto {
    private String placeName;
    private String addressName;
    private String placeUrl;
}
