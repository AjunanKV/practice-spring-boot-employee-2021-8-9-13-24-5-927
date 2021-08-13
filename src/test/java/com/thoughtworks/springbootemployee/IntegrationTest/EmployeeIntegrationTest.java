package com.thoughtworks.springbootemployee.IntegrationTest;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;

    List<Employee> employeeData;
//    @BeforeEach
//    void buildUP(){
//        employeeData = Arrays.asList(new Employee(1,"Kevin",20,"male",9000),
//                new Employee(2,"Jc",21,"male",1000000),
//                new Employee(3,"Janelle",20,"femlae",1000000),
//                new Employee(4,"Charlie",21,"male",1000000));
//
//        employeeRepository.save(employeeData);
//
//    }
    @AfterEach
    void tearDown(){
        employeeRepository.deleteAll();
    }

    @Test
   public void should_return_all_employees_when_get_all_employees_API() throws Exception {
        //given

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Kevin"))
                .andExpect(jsonPath("$[0].age").value(20))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[2].name").value("Janelle"))
                .andExpect(jsonPath("$[2].age").value(20))
                .andExpect(jsonPath("$[2].gender").value("female"));
    }

    @Test
    public void should_create_employee_when_call_create_employee_api() throws Exception {
        String employee = "{\n" +
                " \"id\": 102, \n" +
                " \"name\": \"Kevin\", \n" +
                " \"age\": 20, \n" +
                " \"gender\": \"male\", \n" +
                " \"salary\": 9000\n" +
                "}";

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Kevin"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.gender").value("male"));
    }
    @Test
    void should_return_employee_when_findById_given_employee_id() throws Exception {
        final int id=1;
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Kevin"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.gender").value("male"));
    }

    @Test
    void should_return_employees_when_findByGender_given_employee_gender() throws Exception {
        String gender = "female";
        mockMvc.perform(MockMvcRequestBuilders.get("/employees").param("gender", gender)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].gender", Matchers.hasItem("female"))); //TODO: Matchers.hasItems(gender)
    }

    @Test
    void should_remove_when_removeEmployee_given_employee_id() throws Exception {
        //given
        final Employee employee = new Employee(100, "zero", 100, "male", 999);
        final Employee savedEmployee = employeeRepository.save(employee);
        int id = savedEmployee.getId();
        //when

        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_update_when_updateEmployee_given_employee_information() throws Exception {
        //given
        final Employee employee = new Employee(99, "nine-nine", 0, "female", 1000);
        final Employee savedEmployee = employeeRepository.save(employee);
        String newEmployeeInfo = "{\n" +
                "    \"age\": 99\n" +
                "}";
        //when
        int id = savedEmployee.getId();
        mockMvc.perform(MockMvcRequestBuilders.put("/employees/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newEmployeeInfo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value("99"));
    }

    @Test
    void should_return_two_employee_per_list_when_getListByPagination_given_pageIndex_is_1_and_pageSize_is_2() throws Exception {
        int pageSize = 2;
        int pageIndex = 1;
        mockMvc.perform(MockMvcRequestBuilders.get("/employees")
                .param("pageIndex", String.valueOf(pageIndex)).param("pageSize", String.valueOf(pageSize))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)));
    }

}
