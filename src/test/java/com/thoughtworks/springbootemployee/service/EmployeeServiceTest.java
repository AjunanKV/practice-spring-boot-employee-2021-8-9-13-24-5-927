package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.repository.RetiringEmployeeRepository;
import com.thoughtworks.springbootemployee.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private RetiringEmployeeRepository retiringEmployeeRepository;

    @Test
    public void should_return_all_employees_when_getAllEmployees_given_all_employees() {
        //given
        List<Employee> employees = addItemsInEmployeeList();
        given(retiringEmployeeRepository.getEmployees()).willReturn(employees);

        //when
        List<Employee> actualEmployees = employeeService.getAllEmployees();

        //then
        assertIterableEquals(employees, actualEmployees);
    }


    @Test
    public void should_return_employee_when_get_employee_given_employee_id() {
        //given
        List<Employee> employees = addItemsInEmployeeList();
        given(retiringEmployeeRepository.getEmployees()).willReturn(employees);
        //when
        Employee actualEmployees = employeeService.findEmployeebyID(1);
        //then
        assertEquals(employees.get(0), actualEmployees);
    }

    @Test
    public void should_return_employee_when_get_employee_given_employee_gender() {
        //given
        List<Employee> employees = addItemsInEmployeeList();
        given(retiringEmployeeRepository.getEmployees()).willReturn(employees);
        //when
        List<Employee> actualEmployees = employeeService.findEmployeesByGender("male");
        //then
        assertEquals(employees.get(1).getGender(), actualEmployees.get(0).getGender());
        assertEquals(employees.get(3).getGender(), actualEmployees.get(1).getGender());
    }

    @Test
    void should_return_two_employees_in_a_list_when_getListByPagination_given_pageIndex_is_one_and_page_Size_is_2() {
        List<Employee> employees = addItemsInEmployeeList();

        given(employeeService.getEmployeesWithPageIndexAndPageSize(1, 2)).willReturn(employees.subList(0, 2));
        int expectedCount = 2;
        List<Employee> actualEmployees = employeeService.getEmployeesWithPageIndexAndPageSize(1, 2);
        int outputCount = employeeService.getEmployeesWithPageIndexAndPageSize(1, 2).size();

        assertEquals(outputCount, expectedCount);
        assertEquals(employees.subList(0, 2), actualEmployees);


    }

    @Test
    void should_add_employee_and_add_to_list_when_create_given_employee_information() {
        List<Employee> employees = new ArrayList<>();
        given(retiringEmployeeRepository.getEmployees()).willReturn(employees);
        Employee employeeDetailsToBeAdded = new Employee() {{
            setName("zero");
            setAge(99);
            setGender("Male");
            setSalary(1000);
        }};
        employeeService.addEmployee(employeeDetailsToBeAdded);

        assertEquals(1, employees.size());
        assertEquals(employees.get(0).getId(), 1);
        assertEquals(employeeDetailsToBeAdded.getName(), employees.get(0).getName());
        assertEquals(employeeDetailsToBeAdded.getAge(), employees.get(0).getAge());
        assertEquals(employeeDetailsToBeAdded.getSalary(), employees.get(0).getSalary());
    }

    @Test
    void should_update_employee_when_update_given_employee_information_and_employee_id() {
        List<Employee> employees = addItemsInEmployeeList();
        given(retiringEmployeeRepository.getEmployees()).willReturn(employees);
        Employee updateEmployeeDetails = new Employee() {{
            setName("zero");
            setSalary(1000);
        }};
        Employee updateEmployee = employeeService.updateEmployeeById(1, updateEmployeeDetails);

        assertEquals(employees.get(0).getName(), updateEmployeeDetails.getName());
        assertEquals(employees.get(0).getSalary(), updateEmployeeDetails.getSalary());
        assertEquals(updateEmployee.getName(), updateEmployeeDetails.getName());
        assertEquals(updateEmployee.getSalary(), updateEmployeeDetails.getSalary());
    }

    @Test
    void should_delete_employee_when_delete_given_employee_id() {
        List<Employee> employees = addItemsInEmployeeList();
        given(retiringEmployeeRepository.getEmployees()).willReturn(employees);
        Employee expectedEmployeeToBeDeleted = employeeService.removeEmployee(1);
        assertNotNull(expectedEmployeeToBeDeleted);
        assertEquals(3, employees.size());
        assertEquals(1, expectedEmployeeToBeDeleted.getId());
    }

    private List<Employee> addItemsInEmployeeList() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "alice", 20, "female", 1000));
        employees.add(new Employee(2, "bob", 20, "male", 1000));
        employees.add(new Employee(3, "bobsy", 20, "female", 1000));
        employees.add(new Employee(4, "mark", 20, "male", 1000));
        return employees;
    }

}
