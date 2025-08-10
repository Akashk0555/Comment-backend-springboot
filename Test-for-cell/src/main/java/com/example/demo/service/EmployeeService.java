package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.DTOs.CommentDTO;
import com.example.demo.DTOs.EmployeeDTO;
import com.example.demo.entity.Employee;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<EmployeeDTO> getAllEmployees() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(Long id) {
        Employee emp = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        return mapToDTO(emp);
    }

    public EmployeeDTO createEmployee(EmployeeDTO dto) {
        Employee emp = Employee.builder().name(dto.getName()).build();
        return mapToDTO(repository.save(emp));
    }

    public void deleteEmployee(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found with ID: " + id);
        }
        repository.deleteById(id);
    }

    private EmployeeDTO mapToDTO(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .comments(employee.getComments() == null ? new ArrayList<>() :
                        employee.getComments().stream()
                                .map(comment -> CommentDTO.builder()
                                        .id(comment.getId())
                                        .author(comment.getAuthor())
                                        .text(comment.getText())
                                        .commentDate(comment.getCommentDate())
                                        .createdAt(comment.getCreatedAt())
                                        .build())
                                .toList()
                )
                .build();
    }

}
