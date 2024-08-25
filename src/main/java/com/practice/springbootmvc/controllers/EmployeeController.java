package com.practice.springbootmvc.controllers;

import com.practice.springbootmvc.dto.EmployeeDTO;
import com.practice.springbootmvc.exceptions.ResourceNotFoundException;
import com.practice.springbootmvc.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    public static final int PAGE_SIZE = 5;
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name = "employeeId") Long id) {
        Optional<EmployeeDTO> employeeDTO = employeeService.findEmployeeById(id);
        return employeeDTO.map(ResponseEntity::ok).orElseThrow(() ->
                new ResourceNotFoundException("Employee not found with id : " + id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "id") String sort,
                                                             @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(employeeService.getAllEmployees(createPageable(page, sort, direction)));
    }

    @GetMapping("/age")
    public ResponseEntity<List<EmployeeDTO>>
    getAllEmployeesByAgeBetween(@RequestParam(name = "startAge") int startAge,
                                @RequestParam(name = "endAge") int endAge,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "id") String sort,
                                @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(employeeService.findAllEmployeeByAgeBetween(startAge, endAge,
                createPageable(page, sort, direction)));
    }

    @GetMapping("/salary")
    public ResponseEntity<List<EmployeeDTO>>
    getAllEmployeesBySalaryGreaterThanAndAgeBetween(@RequestParam() double salary,
                                                    @RequestParam(name = "startAge") int startAge,
                                                    @RequestParam(name = "endAge") int endAge,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "id") String sort,
                                                    @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(employeeService.findBySalaryGreaterThanAndAgeBetween(salary, startAge, endAge,
                createPageable(page, sort, direction)));
    }


    @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
        EmployeeDTO savedEmployeeDTO = employeeService.createNewEmployee(employeeDTO);
        return new ResponseEntity<>(savedEmployeeDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody @Valid EmployeeDTO employeeDTO,
                                                          @PathVariable(name = "employeeId") Long id) {
        return ResponseEntity.ok(employeeService.updateEmployeeById(employeeDTO, id));
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable(name = "employeeId") Long id) {
        boolean deleted = employeeService.deleteEmployeeById(id);
        if (deleted) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@PathVariable(name = "employeeId") Long id,
                                                                 @RequestBody @Valid Map<String, Object> updates) {
        EmployeeDTO employeeDTO = employeeService.updatePartialEmployeeById(id, updates);
        if (employeeDTO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);
    }

    private Pageable createPageable(int page, String sort, String direction) {
        return PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.fromString(direction), sort));
    }
}
