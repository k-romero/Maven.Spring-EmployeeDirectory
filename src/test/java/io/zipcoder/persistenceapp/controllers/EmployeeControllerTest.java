package io.zipcoder.persistenceapp.controllers;


import io.zipcoder.persistenceapp.models.Employee;
import io.zipcoder.persistenceapp.services.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    //represents service
    @MockBean
    private EmployeeService service;

    //represents the client
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /employee/21 - Found")
    void testGetEmployeeByIdFound() throws Exception{
        //Setup our mocked service
        Employee mockEmp = new Employee(21,"Kevin","Romero","Dev","3025551234","test@testmail.com","2020-04-01",1);
        doReturn(Optional.of(mockEmp)).when(service).show(21);

        // Execute the GET request
        mockMvc.perform(get("/API/emp/show/{id}",21))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                //Validate the returned fields
                .andExpect(jsonPath("$.id",is(21)))
                .andExpect(jsonPath("$.firstName",is("Kevin")))
                .andExpect(jsonPath("$.lastName",is("Romero")))
                .andExpect(jsonPath("$.title",is("Dev")))
                .andExpect(jsonPath("$.phoneNumber",is("3025551234")))
                .andExpect(jsonPath("$.email",is("test@testmail.com")))
                .andExpect(jsonPath("$.hireDate",is("2020-04-01")))
                .andExpect(jsonPath("$.deptNumber",is(1)));
    }

}