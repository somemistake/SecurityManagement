package com.test.task.service;

import com.test.task.model.History;
import com.test.task.repository.HistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HistoryService {
    private final HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Transactional
    public Optional<History> findById(Long id) {
        return historyRepository.findById(id);
    }

    @Transactional
    public List<History> findAll() {
        return historyRepository.findAll();
    }

    @Transactional
    public History save(History history) {
        return historyRepository.save(history);
    }

    @Transactional
    public void deleteById(Long id) {
        historyRepository.deleteById(id);
    }
}
