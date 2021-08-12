package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.Exceptions.NoEmployeeWithIDException;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.NewEmployeeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Resource
    private EmployeeRepository employeeRepository;
    private NewEmployeeRepository newEmployeeRepository;
    public EmployeeService(EmployeeRepository employeeRepository, NewEmployeeRepository newEmployeeRepository) {
        this.employeeRepository = employeeRepository;
        this.newEmployeeRepository = newEmployeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return newEmployeeRepository.findAll();
    }

    public Employee findEmployeebyID(int employeeId) {
        return newEmployeeRepository.findById(employeeId).orElseThrow(NoEmployeeWithIDException::new);
    }

    public List<Employee> findEmployeesByGender(String gender) {
        return newEmployeeRepository.findAllByGender(gender);

    }

    public List<Employee> getEmployeesWithPageIndexAndPageSize(int pageIndex, int pageSize) {
        int formula = (pageIndex - 1) * pageSize;


        return getAllEmployees()
                .stream()
                .skip(formula).limit(pageSize)
                .collect(Collectors.toList());
    }

    public Employee addEmployee(Employee employee) {
        Employee employeeToBeAdded = new Employee(employeeRepository.getEmployees().size() + 1, employee.getName(),
                employee.getAge(), employee.getGender(), employee.getSalary());
        getAllEmployees().add(employeeToBeAdded);
        return employeeToBeAdded;
    }

    public Employee updateEmployeeById(int employeeId, Employee updateEmployeeDetails) {
        return getAllEmployees().stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst()
                .map(employee -> updateEmployeeInformation(employee, updateEmployeeDetails))
                .orElseThrow(NoEmployeeWithIDException::new);
    }

    private Employee updateEmployeeInformation(Employee employee, Employee employeeUpdate) {
        if (employeeUpdate.getAge() != null) {
            employee.setAge(employeeUpdate.getAge());
        }
        if (employeeUpdate.getGender() != null) {
            employee.setGender(employeeUpdate.getGender());
        }
        if (employeeUpdate.getSalary() != null) {
            employee.setSalary(employeeUpdate.getSalary());
        }
        if (employeeUpdate.getName() != null) {
            employee.setName(employeeUpdate.getName());
        }
        return employee;
    }

    public Employee removeEmployee(Integer employeeId) {
        Employee employeeToBeRemoved = employeeRepository.getEmployees()
                .stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst().orElse(null);
        if (employeeToBeRemoved != null) {
            getAllEmployees().remove(employeeToBeRemoved);
            return employeeToBeRemoved;

        }
        return null;
    }
}
