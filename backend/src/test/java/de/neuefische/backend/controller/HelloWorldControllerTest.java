package de.neuefische.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HelloWorldControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void sayHello_returnsHello() throws Exception {
        // GIVEN

        // WHEN & THEN
        mockMvc.perform(
                get("/api/hello"))
                .andExpect(status().is(200))
                .andExpect(content().string("Hello World! Greetings xoxo - Backend ⭐️"));

    }
}