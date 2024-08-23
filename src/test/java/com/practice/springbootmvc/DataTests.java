package com.practice.springbootmvc;

import com.practice.springbootmvc.entities.EmployeeEntity;
import com.practice.springbootmvc.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class DataTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void isNamePresent() {
        List<EmployeeEntity> name = employeeRepository.findByName("Rohan");
        System.out.println(name);
        assertNotNull(name);

    }

    @Test
    void isEmployeeActive() {
        List<EmployeeEntity> active = employeeRepository.findByIsActive(true);
        assertNotNull(active);
        assertEquals(6, active.size());
    }

    @Test
    void isSalaryGreaterThan() {
        List<EmployeeEntity> salary = employeeRepository.findBySalaryGreaterThan(125000.00);
        assertNotNull(salary);
    }

    @Test
    void isSalaryLessThan() {
        List<EmployeeEntity> salary = employeeRepository.findBySalaryLessThan(15000.00);
//        assertTrue(salary.isEmpty());
        assertThat(salary).isEmpty();
    }

    @Test
    void isSalaryGreaterThanAndAgeLessThan() {
        Long employeeCount = employeeRepository.countBySalaryGreaterThanAndAgeLessThan(100000.00, 25);
        assertEquals(4, employeeCount);
    }

    @Test
    void IsNamePresentWithInitialA() {
        List<EmployeeEntity> name = employeeRepository.findByNameLike("A%");
        assertNotNull(name);
        assertEquals(3, name.size());
    }

    @Test
    void getEmployeeByNameAndEmail(){
        Optional<EmployeeEntity> employee = employeeRepository.findByNameAndEmail("Ayush","ayush@gmail.com");
        System.out.println(employee);
        assertNotNull(employee);
    }

    @Test
    void getEmployeeCountByNameAndEmail(){
        Long count = employeeRepository.countByNameAndEmail("Ayush","ayush@gmail.com");
        assertEquals(1,count);
    }

}
