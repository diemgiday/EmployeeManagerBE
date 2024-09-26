package com.devnguyen.myshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devnguyen.myshop.model.entity.Employee;
import com.devnguyen.myshop.service.EmployeeService;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired 
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<Iterable<Employee>> findAll() {
        Iterable<Employee> employees = employeeService.findAll();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public Optional <Employee> getById(Long id){
        return employeeService.findById(id);
    }

    @PostMapping()
    public String addEmployee(Employee employee){
        employeeService.save(employee);
        return "Add employee successfull";
    }

    @GetMapping("/search")
    public ResponseEntity <List<Employee>> searchEmployees(@RequestParam String keyword){
        try {
            List<Employee> employees = employeeService.searchByText(keyword);
            return ResponseEntity.ok(employees) ;
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
