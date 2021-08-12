package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.Exceptions.NoEmployeeWithIDException;
import com.thoughtworks.springbootemployee.repository.RetiringEmployeeRepository;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.NewEmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Resource
    private RetiringEmployeeRepository retiringEmployeeRepository;
    private NewEmployeeRepository newEmployeeRepository;

    public EmployeeService(RetiringEmployeeRepository retiringEmployeeRepository, NewEmployeeRepository newEmployeeRepository) {
        this.retiringEmployeeRepository = retiringEmployeeRepository;
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
        return newEmployeeRepository.findAll(PageRequest.of(pageIndex - 1, pageSize)).getContent();
    }

    public Employee addEmployee(Employee employee) {
        return newEmployeeRepository.save(employee);
    }

    public Employee updateEmployeeById(int employeeId, Employee updateEmployeeDetails) {
        Employee updateEmployee = newEmployeeRepository.findById(employeeId)
                .map(employee -> updateEmployeeInformation(employee, updateEmployeeDetails))
                .get();
        return newEmployeeRepository.save(updateEmployee);
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
        Optional<Employee> removeEmployee = newEmployeeRepository.findById(employeeId);
        newEmployeeRepository.deleteById(employeeId);
        return removeEmployee.orElse(null);
    }
}
