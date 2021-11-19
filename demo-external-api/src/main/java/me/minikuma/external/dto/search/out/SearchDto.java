package me.minikuma.external.dto.search.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.minikuma.external.dto.search.SearchQueryDto;
import me.minikuma.external.dto.search.SearchQueryResponse;
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


        // SearchQueryDto 로 Converting
        public SearchQueryDto convertSearchQueryDto() {
            return SearchQueryDto.builder()
                    .placeName(this.placeName)
                    .build();
        }
    }

    // SearchQueryDto 기준으로 최종 응답 생성
    public SearchQueryResponse<SearchQueryDto> convertResponse() {
        return SearchQueryResponse.<SearchQueryDto>builder()
                .places(this.documents.stream().map(Documents::convertSearchQueryDto).collect(Collectors.toList()))
                .build();
    }
}
