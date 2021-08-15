package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.Exceptions.NoEmployeeWithIDException;
import com.thoughtworks.springbootemployee.repository.RetiringEmployeeRepository;
import com.thoughtworks.springbootemployee.advice.entity.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Resource
    private RetiringEmployeeRepository retiringEmployeeRepository;
    private final EmployeeRepository employeeRepository;

    public EmployeeService(RetiringEmployeeRepository retiringEmployeeRepository, EmployeeRepository employeeRepository) {
        this.retiringEmployeeRepository = retiringEmployeeRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee findEmployeeByID(int employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(() -> new NoEmployeeWithIDException("No employee found with this ID"));
    }

    public List<Employee> findEmployeesByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
    }

    public List<Employee> getEmployeesWithPageIndexAndPageSize(int pageIndex, int pageSize) {
        return employeeRepository.findAll(PageRequest.of(pageIndex - 1, pageSize)).getContent();
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployeeById(int employeeId, Employee updateEmployeeDetails) {
        Employee updateEmployee = employeeRepository.findById(employeeId)
                .map(employee -> updateEmployeeInformation(employee, updateEmployeeDetails))
                .get(); //TODO: findbyID then update
        return employeeRepository.save(updateEmployee);
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
        Optional<Employee> removeEmployee = employeeRepository.findById(employeeId);
        employeeRepository.deleteById(employeeId);
        return removeEmployee.orElse(null);
    }
}
