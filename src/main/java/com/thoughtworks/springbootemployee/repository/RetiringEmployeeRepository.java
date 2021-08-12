package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class RetiringEmployeeRepository {
    private List<Employee> employees= new ArrayList<>();

    public RetiringEmployeeRepository() {
        employees.add(new Employee(1,"alice",20,"female",1000));
        employees.add(new Employee(2,"bob",20,"male",1000));

        employees.add(new Employee(3,"bobsy",20,"female",1000));
        employees.add(new Employee(4,"mark",20,"male",1000));
    }

    public List<Employee> getEmployees() {
        return employees;
    }

}
