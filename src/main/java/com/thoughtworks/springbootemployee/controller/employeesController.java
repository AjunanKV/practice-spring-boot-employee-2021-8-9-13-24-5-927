package com.thoughtworks.springbootemployee.controller;


import com.thoughtworks.springbootemployee.service.EmployeeService;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeesController {
    @Autowired
    EmployeeService employeeService;

    private List<Employee> employees = new ArrayList<>();

    public EmployeesController() {
        employees.add(new Employee(1,"alice",20,"female",1000));
        employees.add(new Employee(2,"bob",20,"male",1000));

        employees.add(new Employee(3,"bobsy",20,"female",1000));
        employees.add(new Employee(4,"mark",20,"male",1000));
    }
    @GetMapping
    public List<Employee> getAllEmployeeInfo()
    {
        return employeeService.getAllEmployees();
    }

    @GetMapping(path = "/{employeeid}")
    public Employee findbyID(@PathVariable Integer employeeid)
    {
       return employeeService.findEmployeebyID(employeeid); // test first
    }





}