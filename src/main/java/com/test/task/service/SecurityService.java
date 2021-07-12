package com.test.task.service;

import com.test.task.model.Security;
import com.test.task.repository.SecurityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SecurityService {
    private final SecurityRepository securityRepository;

    public SecurityService(SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    @Transactional
    public Optional<Security> findBySecid(String secid) {
        return securityRepository.findBySecid(secid);
    }

    @Transactional
    public List<Security> findAll() {
        return securityRepository.findAll();
    }

    @Transactional
    public Security save(Security security) {
        return securityRepository.save(security);
    }

    @Transactional
    public void deleteBySecid(String secid) {
        securityRepository.deleteBySecid(secid);
    }
}
