package com.test.task.service;

import com.test.task.model.History;
import com.test.task.repository.HistoryRepository;
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
class HistoryServiceTest {
    @MockBean
    private HistoryRepository repository;

    @Autowired
    private HistoryService service;

    @Test
    void shouldFindById() {
        Optional<History> expected = Optional.of(new History());
        when(repository.findById(1l)).thenReturn(expected);
        assertEquals(expected, service.findById(1l));
    }

    @Test
    void shouldFindAll() {
        List<History> expected = new ArrayList<>();
        when(repository.findAll()).thenReturn(expected);
        assertEquals(expected, service.findAll());
    }

    @Test
    void shouldPersistInstance() {
        History expected = new History();
        when(repository.save(expected)).thenReturn(expected);
        assertEquals(expected, service.save(expected));
    }

    @Test
    void shouldDeleteById() {
        doNothing().when(repository).deleteById(1l);
        service.deleteById(1l);
        verify(repository, times(1)).deleteById(1l);
    }
}