package com.practice.springbootmvc.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
        name = "employee_table",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(name = "name_email_unique", columnNames = {"name", "email"})},
        indexes = {@Index(name = "name_index", columnList = "name")}
)
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private Integer age;

    private LocalDate dateOfJoining;

    private Boolean isActive;

    private String department;

    private Double salary;

}
