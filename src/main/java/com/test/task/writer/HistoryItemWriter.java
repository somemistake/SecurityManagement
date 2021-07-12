package com.test.task.writer;

import com.test.task.model.History;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class HistoryItemWriter implements ItemWriter<History> {
    private static final String FIND_SECURITY = "select s from Security s where s.secid = :secid";

    @Autowired
    private EntityManager entityManager;

    @Override
    public void write(List<? extends History> list) throws Exception {
        list.forEach(history -> {
            if (isHistoryExists(history))
                entityManager.merge(history);
            else if (isSecurityExists(history.getSecurity().getSecid()))
                    entityManager.merge(history);
        });
    }

    private boolean isSecurityExists(String secid) {
        return entityManager.createQuery(FIND_SECURITY)
                .setParameter("secid", secid).getResultStream().findAny().isPresent();
    }

    private boolean isHistoryExists(History history) {
        return entityManager.find(History.class, history.getId()) != null;
    }
}
