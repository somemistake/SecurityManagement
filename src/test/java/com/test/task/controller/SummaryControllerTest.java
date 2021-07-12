package com.test.task.controller;

import com.test.task.service.SummaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class SummaryControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private SummaryService service;

    @Autowired
    private SummaryController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldGetAllSummaryBySecid() throws Exception {
        when(service.getAllBySecid("1")).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/summary/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("summaries"))
                .andExpect(view().name("summary/summaryTable"));
    }
}