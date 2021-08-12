package com.thoughtworks.springbootemployee.controller;


import com.thoughtworks.springbootemployee.service.EmployeeService;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeesController {
    @Autowired
    EmployeeService employeeService;

    private List<Employee> employees = new ArrayList<>();

    @GetMapping
    public List<Employee> getAllEmployeeInfo() {
        return employeeService.getAllEmployees();
    }

    @GetMapping(path = "/{employeeid}")
    public Employee findbyID(@PathVariable Integer employeeid) {
        return employeeService.findEmployeebyID(employeeid); // test first
    }

    @GetMapping(params = "employeeGender") // params - no brackets
    public List<Employee> findbyGender(@RequestParam String employeeGender) // request param
    {
        return employeeService.findEmployeesByGender(employeeGender); //test first
    }

    @GetMapping(params = {"pageIndex", "pageSize"})
    public List<Employee> getEmployeesByPageIndex(@RequestParam int pageIndex, @RequestParam int pageSize) {
        return employeeService.getEmployeesWithPageIndexAndPageSize(pageIndex, pageSize);
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @PutMapping(path = "/{employeeId}")
    public Employee updateEmployee(@PathVariable Integer employeeId, @RequestBody Employee employeeToBeupdated) {
        return employeeService.updateEmployeeById(employeeId, employeeToBeupdated);
    }

    @DeleteMapping(path = "/{employeeid}")
    public Employee removeEmployee(@PathVariable Integer employeeid) {
        return employeeService.removeEmployee(employeeid);
    }

}