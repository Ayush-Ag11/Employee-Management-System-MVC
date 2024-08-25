package com.practice.springbootmvc.services;

import com.practice.springbootmvc.dto.EmployeeDTO;
import com.practice.springbootmvc.entities.EmployeeEntity;
import com.practice.springbootmvc.exceptions.ResourceNotFoundException;
import com.practice.springbootmvc.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;


    public EmployeeService(EmployeeRepository employeeRepository, org.modelmapper.ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<EmployeeDTO> findEmployeeById(Long id) {
        return employeeRepository.findById(id).map((employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class)));
    }

    public List<EmployeeDTO> getAllEmployees(Pageable page) {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll(page).getContent();
        return employeeEntities.stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public List<EmployeeDTO> findAllEmployeeByAgeBetween(int startAge, int endAge, Pageable page) {
        List<EmployeeEntity> employeeEntities = employeeRepository.findByAgeBetween(startAge, endAge, page);
        return employeeEntities.stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public List<EmployeeDTO> findBySalaryGreaterThanAndAgeBetween(double salary, int startAge, int endAge, Pageable pageable) {
        List<EmployeeEntity> employeeEntities = employeeRepository.findBySalaryGreaterThanAndAgeBetween(salary, startAge, endAge, pageable);
        return employeeEntities.stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        EmployeeEntity employeeEntity1 = employeeRepository.save(employeeEntity);
        return modelMapper.map(employeeEntity1, EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployeeById(EmployeeDTO employeeDTO, Long id) {
        isExistsById(id);
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        employeeEntity.setId(id);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }

    public void isExistsById(Long id) {
        boolean exists = employeeRepository.existsById(id);
        if (!exists)
            throw new ResourceNotFoundException("Employee not found with id : " + id);
    }


    public boolean deleteEmployeeById(Long id) {
        isExistsById(id);
        employeeRepository.deleteById(id);
        return true;
    }

    public EmployeeDTO updatePartialEmployeeById(Long id, Map<String, Object> updates) {
        isExistsById(id);
        EmployeeEntity employee = employeeRepository.findById(id).get();
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findRequiredField(EmployeeEntity.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, employee, value);
        });
        EmployeeEntity savedEmployee = employeeRepository.save(employee);
        return modelMapper.map(savedEmployee, EmployeeDTO.class);
    }

}
