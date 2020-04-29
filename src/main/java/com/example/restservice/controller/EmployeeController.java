package com.example.restservice.controller;

import com.example.restservice.model.Employee;
import com.example.restservice.repository.EmployeeRepository;
import com.example.restservice.util.EmployeeModelAssembler;
import com.example.restservice.util.EmployeeNotFoundException;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.CollectionModel;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EmployeeController {

    private EmployeeRepository employeeRepository;

    private EmployeeModelAssembler employeeModelAssembler;


    EmployeeController(EmployeeRepository employeeRepository,  EmployeeModelAssembler employeeModelAssembler){
        this.employeeRepository = employeeRepository;
        this.employeeModelAssembler = employeeModelAssembler;
    }

    //Aggregate Root

    @GetMapping(value = "/employees")
    public CollectionModel<EntityModel<Employee>> allEmployees(){
        List<EntityModel<Employee>> employees = this.employeeRepository.findAll().stream()
                .map(employeeModelAssembler::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(employees,
        linkTo(methodOn(EmployeeController.class).allEmployees()).withSelfRel());
    }

    @PostMapping(value = "/employees")
    public Employee newEmployee(@RequestBody Employee newEmployee){
        return this.employeeRepository.save(newEmployee);
    }

    //Single item
    @GetMapping(value = "/employee/{id}")
    public EntityModel<Employee> getEmployee(@PathVariable Long id){
        Employee employee = this.employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return this.employeeModelAssembler.toModel(employee);
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
