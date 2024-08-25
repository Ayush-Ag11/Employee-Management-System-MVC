package com.practice.springbootmvc.dto;

import com.practice.springbootmvc.annotations.EmployeeRoleValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DepartmentDTO {

    private Long id;

    @NotBlank(message = "Name can not be blank")
    @Size(min = 2, max = 9, message = "Number of characters in name should be in the range :[2,9]")
    @EmployeeRoleValidation
    private String name;

    @NotBlank(message = "Location can not be blank")
    private String location;

    private EmployeeDTO manager;

}
