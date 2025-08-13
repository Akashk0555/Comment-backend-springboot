package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.DTOs.CommentDTO;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Employee;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.EmployeeRepository;

@Service
public class CommentService {

    private final CommentRepository commentRepo;
    private final EmployeeRepository employeeRepo;

    public CommentService(CommentRepository commentRepo, EmployeeRepository employeeRepo) {
        this.commentRepo = commentRepo;
        this.employeeRepo = employeeRepo;
    }

    public List<CommentDTO> getCommentsByEmployee(Long employeeId) {
        return commentRepo.findByEmployeeId(employeeId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public CommentDTO addCommentToEmployee(Long employeeId, CommentDTO dto) {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));

        Comment comment = Comment.builder()
                .author(dto.getAuthor())
                .text(dto.getText())
                .commentDate(dto.getCommentDate()) // set from request
                .employee(employee)
                .build();

        return mapToDTO(commentRepo.save(comment));
    }

    public void deleteComment(Long commentId) {
        if (!commentRepo.existsById(commentId)) {
            throw new ResourceNotFoundException("Comment not found with ID: " + commentId);
        }
        commentRepo.deleteById(commentId);
    }

    private CommentDTO mapToDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .author(comment.getAuthor())
                .text(comment.getText())
                .employeeId(comment.getEmployee().getId())
                .createdAt(comment.getCreatedAt())
                .commentDate(comment.getCommentDate())
                .build();
    }
    public List<CommentDTO> getCommentsByEmployeeAndDate(Long employeeId, LocalDate commentDate) {
        List<Comment> comments = commentRepo.findByEmployeeIdAndCommentDate(employeeId, commentDate);
        return comments.stream()
                       .map(this::mapToDTO)
                       .toList();
    }


}
