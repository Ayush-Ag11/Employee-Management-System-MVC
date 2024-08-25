package com.practice.springbootmvc.repositories;

import com.practice.springbootmvc.entities.EmployeeEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    List<EmployeeEntity> findByName(String name);

    List<EmployeeEntity> findByIsActive(boolean isActive);

    List<EmployeeEntity> findBySalaryGreaterThan(double salary);

    List<EmployeeEntity> findBySalaryLessThan(double salary);

    Long countBySalaryGreaterThanAndAgeLessThan(double salary, int age);

    List<EmployeeEntity> findByNameLike(String name);

    @Query("select e from EmployeeEntity e where e.name = ?1 and e.email = ?2")
    Optional<EmployeeEntity> findByNameAndEmail(String name, String email);

    @Query("select count(e) from EmployeeEntity e where e.name= : name and e.email = : email")
    Long countByNameAndEmail(String name, String email);

    @Query("select e from EmployeeEntity e where e.age between ?1 and ?2")
    List<EmployeeEntity> findByAgeBetween(int startAge, int endAge, Pageable page);

    @Query("select e from EmployeeEntity e where e.salary > ?1 and e.age between ?2 and ?3")
    List<EmployeeEntity> findBySalaryGreaterThanAndAgeBetween(double salary, int startAge, int endAge, Pageable pageable);
}
