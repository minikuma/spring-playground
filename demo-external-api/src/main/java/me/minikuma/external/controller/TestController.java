package me.minikuma.external.controller;

import lombok.RequiredArgsConstructor;
import me.minikuma.external.service.TestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/search")
    public ResponseEntity<?> getSearch(@RequestParam String query) {
        return ResponseEntity.ok().body(testService.getSearch(query));
    }
}
