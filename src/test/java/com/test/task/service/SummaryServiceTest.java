package com.test.task.service;

import com.test.task.model.Security;
import com.test.task.model.Summary;
import com.test.task.repository.SecurityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
class SummaryServiceTest {
    @MockBean
    private SecurityRepository repository;

    @Autowired
    private SummaryService service;

    @Test
    void shouldGetAllBySecid() {
        Security expected = new Security();
        expected.setHistories(new HashSet<>());
        when(repository.findBySecid("1")).thenReturn(Optional.of(expected));
        assertEquals(new ArrayList<Summary>(), service.getAllBySecid("1"));
    }
}