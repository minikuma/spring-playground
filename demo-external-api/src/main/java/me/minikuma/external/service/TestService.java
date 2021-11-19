package me.minikuma.external.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.minikuma.external.dto.search.out.SearchDto;
import me.minikuma.external.dto.search.SearchQueryDto;
import me.minikuma.external.dto.search.SearchQueryResponse;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {

    private final RestTemplate restTemplate;

    @Retryable(
            maxAttempts = 3, value = {RuntimeException.class}, backoff = @Backoff(500)
    )
    public SearchQueryResponse<SearchQueryDto> getSearch(String query) {

        // Header Setting
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", "KakaoAK " + "e611ca2e6f7814e882c23dbc0b5491e7");

        // Http Entity
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        // URI
        UriComponents requestBuilder = UriComponentsBuilder.fromHttpUrl("https://dapi.kakao.com/v2/local/search/keyword.json?")
                .queryParam("query", query)
                .queryParam("page", 1)
                .queryParam("size", 10)
                .build();

        int rand = new Random().nextInt();

        try {
            if (rand % 2 == 0) {
                log.error("Error ==> External Server Error");
                throw new RuntimeException("Runtime Exception");
            }
            ResponseEntity<SearchDto> response = restTemplate.exchange(requestBuilder.toUriString(), HttpMethod.GET, httpEntity, SearchDto.class);
            return Objects.requireNonNull(response.getBody()).convertResponse();
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }
    }

    @Recover
    public ResponseEntity<?> recover(Throwable t) {
        log.info("Retry ---- Recover");
        return ResponseEntity.internalServerError().body("서버 에러 발생!");
    }
}
