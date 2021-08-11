package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    public void should_return_all_employees_when_getAllEmployees_given_all_employees() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "alice", 20, "female", 1000));
        employees.add(new Employee(2, "bob", 20, "male", 1000));
        employees.add(new Employee(3, "bobsy", 20, "female", 1000));
        employees.add(new Employee(4, "mark", 20, "male", 1000));
        given(employeeRepository.getEmployees()).willReturn(employees);

        //when
        List<Employee> actualEmployees = employeeService.getAllEmployees();

        //then
        assertIterableEquals(employees, actualEmployees);
    }
    @Test
    public void should_return_employee_when_get_employee_given_employee_id() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "alice", 20, "female", 1000));
        employees.add(new Employee(2, "bob", 20, "male", 1000));
        employees.add(new Employee(3, "bobsy", 20, "female", 1000));
        employees.add(new Employee(4, "mark", 20, "male", 1000));
        given(employeeRepository.getEmployees()).willReturn(employees);
        //when
        Employee actualEmployees =  employeeService.findEmployeebyID(1);
        //then
        assertEquals(employees.get(0),actualEmployees);
    }

    @Test
    public void should_return_employee_when_get_employee_given_employee_gender() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "alice", 20, "female", 1000));
        employees.add(new Employee(2, "bob", 20, "male", 1000));
        employees.add(new Employee(3, "bobsy", 20, "female", 1000));
        employees.add(new Employee(4, "mark", 20, "male", 1000));
        given(employeeRepository.getEmployees()).willReturn(employees);
        //when
        List<Employee> actualEmployees = employeeService.findEmployeesByGender("male");
        //then
        assertEquals(employees.get(1).getGender(),actualEmployees.get(0).getGender());
        assertEquals(employees.get(3).getGender(),actualEmployees.get(1).getGender());
    }

    @Test
    void should_return_two_employees_in_a_list_when_getListByPagination_given_pageIndex_is_one_and_page_Size_is_2() {
        given(employeeRepository.getEmployees())
                .willReturn(Arrays.asList(new Employee(),new Employee(),new Employee(),new Employee()));
        int expectedCount = 2;
        int outputCount = employeeService.getEmployeesWithPageIndexAndPageSize(1, 2).size();
        assertEquals(outputCount, expectedCount);
    }


}
