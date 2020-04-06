package io.zipcoder.persistenceapp.controllers;


import io.zipcoder.persistenceapp.models.Employee;
import io.zipcoder.persistenceapp.services.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

        //WORKING WITH ID 21 DUE TO MY IMPORT SQL WHICH HAS MOCK DATA FOR POSTMAN TESTING
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

    @Test
    @DisplayName("Get /emp/show/21 - Not Found")
    void testGetEmployeeByIdNotFound() throws Exception{
        //Setup our mocked service
        doReturn(Optional.empty()).when(service).show(21);
        //Execute the GET request
        mockMvc.perform(get("/API/emp/show/{id}", 21))
        //Validate that we get a 404 Not Found Response
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Post /API/emp/create - Success")
    void testCreateEmployee() throws Exception{
        //Setup our mocked service
        Employee postEmp = new Employee("Kevin","Romero","Dev","3025551234","test@testmail.com","2020-04-01",1);
        Employee mockEmp = new Employee(21,"Kevin","Romero","Dev","3025551234","test@testmail.com","2020-04-01",1);
        doReturn(mockEmp).when(service).create(any());

        //Execute the POST request
        mockMvc.perform(post("/API/emp/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postEmp)))

            //Validate the response code and content type
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                //Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/API/emp/create/21"))

                //Validate the headers
                .andExpect(jsonPath("$.id", is(21)))
                .andExpect(jsonPath("$.firstName", is("Kevin")))
                .andExpect(jsonPath("$.lastName",is("Romero")))
                .andExpect(jsonPath("$.title",is("Dev")))
                .andExpect(jsonPath("$.phoneNumber",is("3025551234")))
                .andExpect(jsonPath("$.email",is("test@testmail.com")))
                .andExpect(jsonPath("$.hireDate",is("2020-04-01")))
                .andExpect(jsonPath("$.deptNumber",is(1)));

    }

    @Test
    @DisplayName("PUT /API/emp/updateFirstName/21 - Success")
    void testUpdateEmployeeFirstName() throws Exception {
        //Setup our mocked service
        Employee putEmp = new Employee("Adrian","Romero","Dev","3025551234","test@testmail.com","2020-04-01",1);
        Employee mockEmp = new Employee(21,"Kevin","Romero","Dev","3025551234","test@testmail.com","2020-04-01",1);
        doReturn(Optional.of(mockEmp)).when(service).show(21);
        doReturn(mockEmp).when(service).create(any());

        //Execute the PUT request
        mockMvc.perform(put("/API/emp/updateFirstName/{id}",21)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH,21)
                .param("firstName","Adrian")
                .content(asJsonString(putEmp)))

                //Validate that we get a 200 Response
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                //Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/API/emp/updateFirstName/21"))

                .andExpect(jsonPath("$.id", is(21)))
                .andExpect(jsonPath("$.firstName", is("Adrian")));
    }

    @Test
    @DisplayName("PUT /API/emp/updateLastName/21 - Success")
    void testUpdateEmployeeLastName() throws Exception {
        //Setup our mocked service
        Employee putEmp = new Employee("Kevin","Stryder","Dev","3025551234","test@testmail.com","2020-04-01",1);
        Employee mockEmp = new Employee(21,"Kevin","Romero","Dev","3025551234","test@testmail.com","2020-04-01",1);
        doReturn(Optional.of(mockEmp)).when(service).show(21);
        doReturn(mockEmp).when(service).create(any());

        //Execute the PUT request
        mockMvc.perform(put("/API/emp/updateLastName/{id}",21)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH,21)
                .param("lastName","Stryder")
                .content(asJsonString(putEmp)))

                //Validate that we get a 200 Response
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                //Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/API/emp/updateLastName/21"))

                .andExpect(jsonPath("$.id", is(21)))
                .andExpect(jsonPath("$.lastName", is("Stryder")));
    }

    @Test
    @DisplayName("PUT /API/emp/updatePhone/21 - Success")
    void testUpdateEmployeePhone() throws Exception {


        //Setup our mocked service
        Employee putEmp = new Employee(21,"Kevin","Stryder","Dev","5550005555","test@testmail.com","2020-04-01",1);
        Employee mockEmp = new Employee(21,"Kevin","Romero","Dev","3025551234","test@testmail.com","2020-04-01",1);
        doReturn(Optional.of(mockEmp)).when(service).show(21);
        doReturn(mockEmp).when(service).create(any());

        //Execute the PUT request
        mockMvc.perform(put("/API/emp/updatePhone/{id}",21)
                .header(HttpHeaders.IF_MATCH,21)
                .param("phone","5550005555")
                .content(asJsonString(putEmp)))

                //Validate that we get a 200 Response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(21)))
                .andExpect(jsonPath("$.phone", is("5550005555")));
    }

    @Test
    @DisplayName("DELETE /API/emp/removeEmp/21")
    void testDelete() throws Exception {
        //Setup our mocked employee
        Employee mockEmp = new Employee(21,"Kevin","Romero","Dev","3025551234","test@testmail.com","2020-04-01",1);

        //Setup the mocked service
        doReturn(Optional.of(mockEmp)).when(service).show(21);
        doReturn(true).when(service).delete(21);

        //Execute the DELETE request
        mockMvc.perform(delete("/API/emp/removeEmp/{id}",21))
                .andExpect(status().isOk());

    }


    //Setup

    //Execute

    //Validate


    static String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}