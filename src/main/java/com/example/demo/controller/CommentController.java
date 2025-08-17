package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTOs.CommentDTO;
import com.example.demo.DTOs.CommentRangeRequestDTO;
import com.example.demo.service.CommentService;

import jakarta.validation.Valid;

@CrossOrigin("http://localhost:4200/")
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
    @GetMapping("/date/{commentDate}")
    public List<CommentDTO> getCommentsByEmployeeAndDate(
            @PathVariable Long employeeId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate commentDate) {
        return service.getCommentsByEmployeeAndDate(employeeId, commentDate);
    }
    @PostMapping("/range")
    public ResponseEntity<String> addCommentForDateRange(@Valid @RequestBody CommentRangeRequestDTO request) {
        service.addCommentForDateRange(request);
        return ResponseEntity.ok("Comments added for date range successfully");
    }
    
//    @PostMapping("/employees/{employeeId}/comments/{startDate}/{endDate}")
//    public ResponseEntity<Void> addCommentForDateRange(
//            @PathVariable Long employeeId,
//            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
//            @Valid @RequestBody CommentDTO commentDTO) {
//
//        service.addCommentForDateRange(employeeId, startDate, endDate, commentDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }



    @PostMapping
    public CommentDTO addComment(@PathVariable Long employeeId, @Valid @RequestBody CommentDTO dto) {
        return service.addCommentToEmployee(employeeId, dto);
    }
    
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(
            @PathVariable Long employeeId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentDTO dto) {

        CommentDTO updatedComment = service.updateComment(employeeId, commentId, dto);
        return ResponseEntity.ok(updatedComment);
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long employeeId,
            @PathVariable Long commentId) {
        service.deleteComment(commentId, employeeId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
