package com.test.task.repository;

import com.test.task.model.History;
import com.test.task.model.Security;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class HistoryRepositoryTest {
    @Autowired
    private HistoryRepository repository;

    @AfterAll
    @Sql("/test/sql/cleanTables.sql")
    static void cleanUp() {

    }

    @Test
    void shouldPersistHistory() {
        History history = new History();
        history.setId(1l);
        History actual = repository.save(history);
        assertEquals(1l, actual.getId());
    }

    @Test
    void shouldThrowInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> repository.save(null));
        History history = new History(){{setSecurity(new Security());}};
        assertThrows(InvalidDataAccessApiUsageException.class, () -> repository.save(history));
    }

    @Test
    @Sql(scripts = {"/test/sql/cleanTables.sql", "/test/sql/history/insertList.sql"})
    void shouldFindAllHistories() {
        Security security = new Security();
        security.setId(1l);
        security.setSecid("1");
        security.setRegnumber("1");
        security.setName("1");
        security.setEmitentTitle("1");

        History history1 = new History();
        history1.setId(1l);
        history1.setSecurity(security);
        history1.setTradedate(Date.valueOf("2020-12-12"));
        history1.setNumtrades(1);
        history1.setOpen(1d);
        history1.setClose(2d);

        History history2 = new History();
        history2.setId(2l);
        history2.setSecurity(security);
        history2.setTradedate(Date.valueOf("2020-12-13"));
        history2.setNumtrades(2);
        history2.setOpen(2d);
        history2.setClose(3d);

        List<History> expected = new ArrayList<>();
        expected.add(history1);
        expected.add(history2);

        assertEquals(expected, repository.findAll());
    }

    @Test
    @Sql("/test/sql/cleanTables.sql")
    void shouldReturnEmptyList() {
        List<History> expected = new ArrayList<>();
        assertEquals(expected, repository.findAll());
    }

    @Test
    @Sql(scripts = {"/test/sql/cleanTables.sql", "/test/sql/history/insertEntity.sql"})
    void shouldFindById() {
        Security security = new Security();
        security.setId(1l);
        security.setSecid("1");
        security.setRegnumber("1");
        security.setName("1");
        security.setEmitentTitle("1");

        History expected = new History();
        expected.setId(1l);
        expected.setSecurity(security);
        expected.setTradedate(Date.valueOf("2020-12-12"));
        expected.setNumtrades(1);
        expected.setOpen(1d);
        expected.setClose(2d);
        assertEquals(Optional.of(expected), repository.findById(1l));
    }

    @Test
    @Sql("/test/sql/cleanTables.sql")
    void shouldReturnEmptyOptional() {
        assertEquals(Optional.empty(), repository.findById(2000l));
    }

    @Test
    @Sql(scripts = {"/test/sql/cleanTables.sql", "/test/sql/history/insertEntity.sql"})
    void shouldDeleteById() {
        repository.deleteById(1l);
        assertEquals(Optional.empty(), repository.findById(1l));
    }
}