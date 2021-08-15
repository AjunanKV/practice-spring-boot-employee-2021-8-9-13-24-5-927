package com.thoughtworks.springbootemployee.model;

public class EmployeeRequest {
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;
    private Integer companyid;


   public EmployeeRequest(){

   }

    public EmployeeRequest(String name, Integer age, String gender, Integer salary, Integer companyid) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.companyid = companyid;
    }
}
