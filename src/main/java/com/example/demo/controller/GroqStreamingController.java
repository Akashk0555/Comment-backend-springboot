package com.example.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.GroqStreamingService;

import reactor.core.publisher.Flux;

@RestController
@CrossOrigin("http://localhost:4200")
public class GroqStreamingController {

	private final GroqStreamingService streamingService;

    public GroqStreamingController(GroqStreamingService streamingService) {
        this.streamingService = streamingService;
    }

    @GetMapping(value = "/groq/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(@RequestParam String q) {
        return streamingService.streamGroq(q);
    }
}