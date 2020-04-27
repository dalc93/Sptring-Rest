package com.example.restservice.controller;

import com.example.restservice.model.Employee;
import com.example.restservice.repository.EmployeeRepository;
import com.example.restservice.util.EmployeeNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    private EmployeeRepository employeeRepository;

    EmployeeController(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    //Aggregate Root

    @GetMapping(value = "/employees")
    List<Employee> allEmployees(){
        return this.employeeRepository.findAll();
    }

    @PostMapping(value = "/employees")
    Employee newEmployee(@RequestBody Employee newEmployee){
        return this.employeeRepository.save(newEmployee);
    }

    //Single item
    @GetMapping(value = "/employee/{id}")
    Employee getEmployee(@PathVariable Long id){
        return this.employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @PutMapping(value = "/employees/{id}")
    Employee updateEmployee(@RequestBody Employee newEmployee, Long id){
        return this.employeeRepository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return this.employeeRepository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return this.employeeRepository.save(newEmployee);
                });
    }

    @DeleteMapping(value = "/employees/{id}")
    void deleteEmployee(@PathVariable Long id){
        this.employeeRepository.deleteById(id);
    }
}
