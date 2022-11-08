package de.neuefische.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AppUserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void WithoutLogin_ShouldReturn_Unauthorized() throws Exception {

        mockMvc.perform(get("/api/photo/11"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "hrhee", password = "ABC123")
    void WithLogin_ShouldReturn_Authorized() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.get("/api/found-routes"))
                .andExpect(status().isOk());
    }

}