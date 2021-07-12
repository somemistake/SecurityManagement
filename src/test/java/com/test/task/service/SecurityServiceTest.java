package com.test.task.service;

import com.test.task.model.Security;
import com.test.task.repository.SecurityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
class SecurityServiceTest {
    @MockBean
    private SecurityRepository repository;

    @Autowired
    private SecurityService service;

    @Test
    void shouldFindById() {
        Optional<Security> expected = Optional.of(new Security());
        when(repository.findBySecid("1")).thenReturn(expected);
        assertEquals(expected, service.findBySecid("1"));
    }

    @Test
    void shouldFindAll() {
        List<Security> expected = new ArrayList<>();
        when(repository.findAll()).thenReturn(expected);
        assertEquals(expected, service.findAll());
    }

    @Test
    void shouldPersistInstance() {
        Security expected = new Security();
        when(repository.save(expected)).thenReturn(expected);
        assertEquals(expected, service.save(expected));
    }

    @Test
    void shouldDeleteById() {
        doNothing().when(repository).deleteBySecid("1");
        service.deleteBySecid("1");
        verify(repository, times(1)).deleteBySecid("1");
    }
}