package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.GroqService;

@RestController
@RequestMapping("/groq")
@CrossOrigin(origins = "http://localhost:4200")
public class GroqController {

    private final GroqService groqService;

    public GroqController(GroqService groqService) {
        this.groqService = groqService;
    }

    @GetMapping("/ask")
    public ResponseEntity<String> ask(@RequestParam String q) {
        String response = groqService.askGroq(q);
        return ResponseEntity.ok(response);
    }
}