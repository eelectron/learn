package com.example.sse.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1")
public class SseController {

    @GetMapping("/stream")
    public Flux<String> stream() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(i -> "Hello " + i + "!");
    }
}
