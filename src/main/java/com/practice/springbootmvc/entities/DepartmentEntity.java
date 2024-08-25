package com.practice.springbootmvc.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "department_table",
        uniqueConstraints = {@UniqueConstraint(columnNames = "name")},
        indexes = {@Index(name = "name_index", columnList = "name")})
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String location;

    @OneToOne
    @JoinColumn(name = "department_manager")
    private EmployeeEntity manager;
}
