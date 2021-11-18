package me.minikuma.external.service;

import lombok.RequiredArgsConstructor;
import me.minikuma.external.dto.ResponseDto;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class TestService {

    private final RestTemplate restTemplate;

    public ResponseEntity<?> getString() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", "KakaoAK " + "e611ca2e6f7814e882c23dbc0b5491e7");

        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        UriComponents requestBuilder = UriComponentsBuilder.fromHttpUrl("https://dapi.kakao.com/v2/local/search/keyword.json?")
                .queryParam("query", "자전거")
                .queryParam("page", 1)
                .queryParam("size", 1)
                .build();

        try {
            ResponseEntity<ResponseDto> response = restTemplate.exchange(requestBuilder.toUriString(), HttpMethod.GET, httpEntity, ResponseDto.class);
            return response;
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }
    }
}
