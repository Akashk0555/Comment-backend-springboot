package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTOs.CommentDTO;
import com.example.demo.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees/{employeeId}/comments")
public class CommentController {

    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping
    public List<CommentDTO> getComments(@PathVariable Long employeeId) {
        return service.getCommentsByEmployee(employeeId);
    }

    @PostMapping
    public CommentDTO addComment(@PathVariable Long employeeId, @Valid @RequestBody CommentDTO dto) {
        return service.addCommentToEmployee(employeeId, dto);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        service.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
