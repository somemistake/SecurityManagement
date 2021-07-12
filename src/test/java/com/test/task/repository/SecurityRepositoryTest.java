package com.test.task.repository;

import com.test.task.model.Security;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class SecurityRepositoryTest {
    @Autowired
    private SecurityRepository repository;

    @AfterAll
    @Sql("/test/sql/cleanTables.sql")
    static void cleanUp() {

    }

    @Test
    void shouldPersistSecurity() {
        Security security = new Security();
        security.setId(1l);
        security.setSecid("1");
        Security actual = repository.save(security);
        assertEquals(1l, actual.getId());
        assertEquals("1", actual.getSecid());
    }

    @Test
    void shouldThrowInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> repository.save(null));
    }

    @Test
    @Sql(scripts = {"/test/sql/cleanTables.sql", "/test/sql/security/insertList.sql"})
    void shouldFindAllSecurities() {
        Security security1 = new Security();
        security1.setId(1l);
        security1.setSecid("1");
        security1.setRegnumber("1");
        security1.setName("1");
        security1.setEmitentTitle("1");

        Security security2 = new Security();
        security2.setId(2l);
        security2.setSecid("2");
        security2.setRegnumber("2");
        security2.setName("2");
        security2.setEmitentTitle("2");

        List<Security> expected = new ArrayList<>();
        expected.add(security1);
        expected.add(security2);

        assertEquals(expected, repository.findAll());
    }

    @Test
    @Sql("/test/sql/cleanTables.sql")
    void shouldReturnEmptyList() {
        List<Security> expected = new ArrayList<>();
        assertEquals(expected, repository.findAll());
    }

    @Test
    @Sql(scripts = {"/test/sql/cleanTables.sql", "/test/sql/security/insertEntity.sql"})
    void shouldFindBySecid() {
        Security expected = new Security();
        expected.setId(1l);
        expected.setSecid("1");
        expected.setRegnumber("1");
        expected.setName("1");
        expected.setEmitentTitle("1");

        assertEquals(Optional.of(expected), repository.findBySecid("1"));
    }

    @Test
    @Sql("/test/sql/cleanTables.sql")
    void shouldReturnEmptyOptional() {
        assertEquals(Optional.empty(), repository.findBySecid("2000"));
    }

    @Test
    @Sql(scripts = {"/test/sql/cleanTables.sql", "/test/sql/security/insertEntity.sql"})
    void shouldDeleteBySecid() {
        repository.deleteBySecid("1");
        assertEquals(Optional.empty(), repository.findBySecid("1"));
    }
}