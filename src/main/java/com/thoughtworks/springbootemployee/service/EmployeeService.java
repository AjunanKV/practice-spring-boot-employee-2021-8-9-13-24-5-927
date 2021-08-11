package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees()
    {
        return employeeRepository.getEmployees();
    }


    public Employee findEmployeebyID(int employeeId) {
        return employeeRepository.getEmployees().stream()
                .filter(employee -> employee.getId().equals(employeeId)).findAny().orElse(null);
    }

    public List<Employee> findEmployeesByGender(String gender)
    {
        return employeeRepository.getEmployees().stream()
                .filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
    }

    public List<Employee> getEmployeesWithPageIndexAndPageSize(int pageIndex, int pageSize)
    {
        return employeeRepository.getEmployees().stream()
                .skip((pageIndex -1) *pageSize).limit(pageSize).collect(Collectors.toList());
    }

    public Employee addEmployee(Employee employee) {
        Employee employeeToBeAdded = new Employee(employeeRepository.getEmployees().size() + 1, employee.getName(),
                employee.getAge(), employee.getGender(), employee.getSalary());
        employeeRepository.getEmployees().add(employeeToBeAdded);
        return employeeToBeAdded;
    }

}
