package com.practice.springbootmvc.controllers;

import com.practice.springbootmvc.dto.DepartmentDTO;
import com.practice.springbootmvc.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    public static final int PAGE_SIZE = 5;
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> findAllDepartments(@RequestParam(defaultValue = "0") int page){
        return ResponseEntity.ok(departmentService.findAllDepartments(PageRequest.of(page, PAGE_SIZE)));
    }

    @GetMapping(path = "/{departmentId}")
    public ResponseEntity<DepartmentDTO> findDepartmentById(@PathVariable Long departmentId){
        DepartmentDTO departmentDTO = departmentService.findById(departmentId);
        return new ResponseEntity<>(departmentDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody @Valid DepartmentDTO departmentDTO) {
        DepartmentDTO newdepartmentDTO = departmentService.createNewDepartment(departmentDTO);
        return new ResponseEntity<>(newdepartmentDTO, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{departmentId}/manager/{employeeId}")
    public ResponseEntity<DepartmentDTO> assignManagerToDepartment(@PathVariable Long departmentId
                                                                 , @PathVariable Long employeeId){
        DepartmentDTO departmentDTO = departmentService.assignManagerToDepartment(departmentId, employeeId);
        return new ResponseEntity<>(departmentDTO, HttpStatus.OK);
    }
}
