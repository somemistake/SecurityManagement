package com.test.task.service;

import com.test.task.model.Security;
import com.test.task.model.Summary;
import com.test.task.repository.SecurityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SummaryService {
    private final SecurityRepository securityRepository;

    public SummaryService(SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    public List<Summary> getAllBySecid(String secid) {
        Security security = securityRepository.findBySecid(secid).get();
        List<Summary> summaries = security
                .getHistories()
                .stream()
                .map(history -> {
                    Summary summary = new Summary();
                    summary.setSecid(security.getSecid());
                    summary.setRegnumber(security.getRegnumber());
                    summary.setName(security.getName());
                    summary.setEmitentTitle(security.getEmitentTitle());
                    summary.setTradedate(history.getTradedate());
                    summary.setNumtrades(history.getNumtrades());
                    summary.setOpen(history.getOpen());
                    summary.setClose(history.getClose());
                    return summary;
                })
                .collect(Collectors.toList());
        if (summaries.isEmpty()) {
            Summary summary = new Summary();
            summary.setSecid(security.getSecid());
            summary.setRegnumber(security.getRegnumber());
            summary.setName(security.getName());
            summary.setEmitentTitle(security.getEmitentTitle());
            summaries.add(summary);
        }
        return summaries;
    }
}
