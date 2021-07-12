package com.test.task.controller;

import com.test.task.model.History;
import com.test.task.model.Security;
import com.test.task.service.HistoryService;
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
class HistoryControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private HistoryService historyService;

    @MockBean
    private SecurityService securityService;

    @Autowired
    private HistoryController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldGetHistoryHomePage() throws Exception {
        mockMvc.perform(get("/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("history/historyTable"));
    }

    @Test
    void shouldGetHistoryCreateForm() throws Exception {
        mockMvc.perform(get("/history/createHistoryForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("history/createHistoryForm"));
    }

    @Test
    void shouldCreateHistoryAndRedirectHistoryHomePage() throws Exception {
        when(securityService.findBySecid("1")).thenReturn(Optional.of(new Security()));
        mockMvc.perform(post("/history/createHistory")
                .param("tradedate", "2020-12-12")
                .param("secid", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("history"))
                .andExpect(redirectedUrl("/history?secid=1&tradedate=2020-12-12"));
    }

    @Test
    void shouldGetHistoryEditForm() throws Exception {
        when(historyService.findById(1l)).thenReturn(Optional.of(new History()));
        mockMvc.perform(get("/history/editHistoryForm/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("history/editHistoryForm"));
    }

    @Test
    void shouldEditHistoryAndRedirectHistoryHomePage() throws Exception {
        when(securityService.findBySecid("1")).thenReturn(Optional.of(new Security()));
        mockMvc.perform(post("/history/editHistory")
                .param("tradedate", "2020-12-12")
                .param("secid", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("history"))
                .andExpect(redirectedUrl("/history?secid=1&tradedate=2020-12-12"));
    }

    @Test
    void shouldDeleteHistoryAndRedirectHistoryHomePage() throws Exception {
        mockMvc.perform(get("/history/deleteHistory/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/history"));
    }
}