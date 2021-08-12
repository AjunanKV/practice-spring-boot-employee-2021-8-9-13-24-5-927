package com.thoughtworks.springbootemployee.IntegrationTest;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
   public void should_return_all_employees_when_get_all_employees_API() throws Exception {
        //given

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Cedric"))
                .andExpect(jsonPath("$[0].age").value(21))
                .andExpect(jsonPath("$[0].gender").value("female"))
                .andExpect(jsonPath("$[2].name").value("Janelle"))
                .andExpect(jsonPath("$[2].age").value(20))
                .andExpect(jsonPath("$[2].gender").value("female"));
    }
//    @Test
//    public void should_return_employee_when_get_employee_given_employee_id() throws Exception {
//
//        //given
//        int tempId = 1;
//        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{employeeid}/",tempId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].name").value("Cedric"))
//                .andExpect(jsonPath("$[0].age").value(19))
//                .andExpect(jsonPath("$[0].gender").value("male"));
//    }
    @Test
    public void should_create_employee_when_call_create_employee_api() throws Exception {
        String employee = "{\n" +
                " \"id\": 1, \n" +
                " \"name\": \"Linne\", \n" +
                " \"age\": 20, \n" +
                " \"gender\": \"female\", \n" +
                " \"salary\": 1000\n" +
                "}";

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[4].name").value("Linne"))
                .andExpect(jsonPath("$[4].age").value(20))
                .andExpect(jsonPath("$[4].gender").value("female"));
    }


}
