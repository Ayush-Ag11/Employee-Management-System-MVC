package com.practice.springbootmvc.annotations;

import com.practice.springbootmvc.entities.enums.DepartmentEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeRoleValidator implements ConstraintValidator<EmployeeRoleValidation, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) return false;
        List<String> roles = List.of(DepartmentEnum.values()).stream().map(Enum::name).collect(Collectors.toList());
        return roles.contains(s);
    }
}
