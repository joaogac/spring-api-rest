package br.com.jgconsulting.springapirest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({ SpringExtension.class })
@WebMvcTest(CountryController.class)
class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        System.out.println("Calling @BeforeEach - setUp() method.. ");
    }

    @Test
    void getAll() throws Exception {
        final RequestBuilder request = MockMvcRequestBuilders.get("/v1/countries");
        final MvcResult result = mockMvc.perform(request).andReturn();

        assertAll(
                () -> assertNotNull( result.getResponse().getContentAsString() ),
                () -> assertEquals( HttpStatus.OK.value(), result.getResponse().getStatus() )
        );
    }
}