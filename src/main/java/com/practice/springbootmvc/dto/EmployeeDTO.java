package com.practice.springbootmvc.dto;

import com.practice.springbootmvc.annotations.EmployeeRoleValidation;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "Name of employee can not be blank")
    private String name;

    @Email(message = "email should be a valid email")
    private String email;

    @Size(min = 2, max = 3, message = "Number of characters in age should be in the range :[2,3]")
    @Max(value = 60, message = "age cannot be more than 60")
    @Min(value = 18, message = "age cannot be less than 18")
    private Integer age;

    @PastOrPresent(message = "Date of joining can not be in the future")
    private LocalDate dateOfJoining;

    @AssertTrue(message = "Employee should be active")
    private Boolean isActive;

    @NotBlank(message = "role of employee can not be blank")
    @EmployeeRoleValidation
    private String department;

    @Digits(integer = 8, fraction = 2, message = "Employee salary cannot have more than 8 digits")
    @DecimalMin(value = "10000.99")
    @DecimalMax(value = "10000000.99")
    private Double salary;

}
