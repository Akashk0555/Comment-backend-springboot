package com.example.demo.DTOs;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {

    private Long id;

    @NotBlank(message = "Author is required")
    @Size(max = 50, message = "Author name must be less than 50 characters")
    private String author;

    @NotBlank(message = "Text is required")
    @Size(max = 1000, message = "Comment must be less than 1000 characters")
    private String text;

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Comment date is required")
    private LocalDate commentDate;

    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
// system timestamp
}