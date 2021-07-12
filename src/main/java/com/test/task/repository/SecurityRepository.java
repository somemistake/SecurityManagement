package com.test.task.repository;

import com.test.task.model.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityRepository extends JpaRepository<Security, Long> {
    Optional<Security> findBySecid(String secid);

    void deleteBySecid(String secid);
}
