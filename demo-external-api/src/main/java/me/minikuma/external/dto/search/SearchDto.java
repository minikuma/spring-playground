package me.minikuma.external.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchDto {
    @JsonProperty("documents")
    private List<Documents> documents;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Documents {
        @JsonProperty("place_name")
        private String placeName;

        public SearchQueryDto convertSearchQueryDto() {
            return SearchQueryDto.builder()
                    .placeName(this.placeName)
                    .build();
        }
    }

    public SearchQueryResponse<SearchQueryDto> convertResponse(ResponseEntity<SearchDto> response) {
        return SearchQueryResponse.<SearchQueryDto>builder()
                .places(this.documents.stream().map(Documents::convertSearchQueryDto).collect(Collectors.toList()))
                .build();
    }
}
