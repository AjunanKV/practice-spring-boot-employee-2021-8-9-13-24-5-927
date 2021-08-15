package com.thoughtworks.springbootemployee.IntegrationTest;

import com.thoughtworks.springbootemployee.advice.entity.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;

import org.hamcrest.Matchers;
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
import java.util.Optional;

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

    @BeforeEach
        public void createEmplyoees(){
        List<Employee> employeeList = Arrays.asList((new Employee(1, "Kevin", 20, "male", 999)),
                new Employee(2, "Jc", 21, "male", 999),
                new Employee(3, "Janelle", 20, "female", 999),
                new Employee(4, "Charlie", 21, "male", 999));
        employeeRepository.saveAll(employeeList);
    }

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
                " \"id\": 5, \n" +
                " \"age\": 20, \n" +
                " \"gender\": \"male\", \n" +
                " \"name\": \"Kevin\", \n" +
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
       List<Employee> employees = employeeRepository.findAll();
       int id = employees.get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{employeeid}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Kevin"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.gender").value("male"));
    }
    @Test
    void should_return_employees_when_findByGender_given_employee_gender() throws Exception {
        String gender = "male";
        mockMvc.perform(MockMvcRequestBuilders.get("/employees").param("gender", gender)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].gender", Matchers.hasItem("male")));
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
        final Employee employee = new Employee(5, "Kevin", 20, "male", 999);
        String newEmployeeInfo = "{\n" +
                "    \"age\": 99\n" +
                "}";

        //when
        final Employee savedEmployee = employeeRepository.save(employee);
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
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Kevin"))
                .andExpect(jsonPath("$[1].name").value("Jc"));
    }

}
