package com.thoughtworks.springbootemployee.controller;


import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.EmployeeResponse;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import com.thoughtworks.springbootemployee.advice.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeesController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    EmployeeMapper employeeMapper;
    private List<Employee> employees = new ArrayList<>();

    @GetMapping
    public List<Employee> getAllEmployeeInfo() {
        return employeeService.getAllEmployees();
    }

    @GetMapping(path = "/{employeeid}")
    public EmployeeResponse findbyID(@PathVariable Integer employeeid) {
        return employeeMapper.toResponse(employeeService.findEmployeeByID(employeeid));
    }

    @GetMapping(params = "employeeGender")
    public List<EmployeeResponse> findbyGender(@RequestParam String employeeGender) {
        return employeeMapper.toResponseList(employeeService.findEmployeesByGender(employeeGender));
    }

    @GetMapping(params = {"pageIndex", "pageSize"})
    public List<EmployeeResponse> getEmployeesByPageIndex(@RequestParam int pageIndex, @RequestParam int pageSize) {
        return employeeMapper.toResponseList(employeeService.getEmployeesWithPageIndexAndPageSize(pageIndex, pageSize));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public EmployeeResponse addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        Employee employee = employeeService.addEmployee(employeeMapper.toEntity(employeeRequest));
        return employeeMapper.toResponse(employee);

    }

    @PutMapping(path = "/{employeeId}")
    public EmployeeResponse updateEmployee(@PathVariable Integer employeeId, @RequestBody EmployeeRequest employeeRequest) {
        return employeeMapper.toResponse(employeeService.updateEmployeeById(employeeId, employeeMapper.toEntity(employeeRequest)));
    }

    @DeleteMapping(path = "/{employeeid}")
    public EmployeeResponse removeEmployee(@PathVariable Integer employeeid) {
        Employee employee = employeeService.removeEmployee(employeeid);
        return employeeMapper.toResponse(employee);
    }

}