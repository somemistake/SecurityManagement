package com.test.task.controller;

import com.test.task.model.Security;
import com.test.task.service.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class SecurityControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private SecurityService service;

    @Autowired
    private SecurityController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldGetSecurityHomePage() throws Exception {
        mockMvc.perform(get("/security"))
                .andExpect(status().isOk())
                .andExpect(view().name("security/securityTable"));
    }

    @Test
    void shouldGetSecurityCreateForm() throws Exception {
        mockMvc.perform(get("/security/createSecurityForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("security/createSecurityForm"));
    }

    @Test
    void shouldCreateSecurityAndRedirectSecurityHomePage() throws Exception {
        mockMvc.perform(post("/security/createSecurity"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("security"))
                .andExpect(redirectedUrl("/security"));
    }

    @Test
    void shouldGetSecurityEditForm() throws Exception {
        when(service.findBySecid("1")).thenReturn(Optional.of(new Security()));
        mockMvc.perform(get("/security/editSecurityForm/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("security/editSecurityForm"));
    }

    @Test
    void shouldEditSecurityAndRedirectSecurityHomePage() throws Exception {
        mockMvc.perform(post("/security/editSecurity"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("security"))
                .andExpect(redirectedUrl("/security"));
    }

    @Test
    void shouldDeleteSecurityAndRedirectSecurityHomePage() throws Exception {
        mockMvc.perform(get("/security/deleteSecurity/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/security"));
    }
}