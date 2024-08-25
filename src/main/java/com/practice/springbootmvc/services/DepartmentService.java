package com.practice.springbootmvc.services;

import com.practice.springbootmvc.dto.DepartmentDTO;
import com.practice.springbootmvc.entities.DepartmentEntity;
import com.practice.springbootmvc.entities.EmployeeEntity;
import com.practice.springbootmvc.exceptions.ResourceNotFoundException;
import com.practice.springbootmvc.repositories.DepartmentRepository;
import com.practice.springbootmvc.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public List<DepartmentDTO> findAllDepartments(Pageable pageable) {
        List<DepartmentEntity> departmentEntities = departmentRepository.findAll(pageable).getContent();
        return departmentEntities.stream()
                .map(departmentEntity -> modelMapper.map(departmentEntity, DepartmentDTO.class))
                .collect(Collectors.toList());
    }

    public DepartmentDTO createNewDepartment(DepartmentDTO departmentDTO) {
        DepartmentEntity departmentEntity = modelMapper.map(departmentDTO, DepartmentEntity.class);
        DepartmentEntity departmentEntity1 = departmentRepository.save(departmentEntity);
        return modelMapper.map(departmentEntity1, DepartmentDTO.class);
    }

    public DepartmentDTO assignManagerToDepartment(Long departmentId, Long employeeId) {
        DepartmentEntity departmentEntity = departmentRepository.findById(departmentId).orElseThrow(() ->
                new ResourceNotFoundException("Department not found with id : " + departmentId));
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).orElseThrow(() ->
                new ResourceNotFoundException("Employee not found with id : " + employeeId));
        departmentEntity.setManager(employeeEntity);
        DepartmentEntity savedDepartmentEntity = departmentRepository.save(departmentEntity);
        return modelMapper.map(savedDepartmentEntity, DepartmentDTO.class);

    }

    public DepartmentDTO findById(Long departmentId) {
        DepartmentEntity departmentEntity = departmentRepository.findById(departmentId).orElseThrow(() ->
                new ResourceNotFoundException("Department not found with id : " + departmentId));
        return modelMapper.map(departmentEntity, DepartmentDTO.class);
    }
}
