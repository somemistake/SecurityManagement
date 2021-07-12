package com.test.task.processor;

import com.test.task.model.History;
import com.test.task.model.Security;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class HistoryItemProcessor implements ItemProcessor<History, History> {
    private final EntityManager entityManager;

    public HistoryItemProcessor(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public History process(History history) throws Exception {
        Security security = findBySecid(history.getSecurity().getSecid());
        if (security != null) history.setSecurity(security);
        return history;
    }

    private Security findBySecid(String secid) {
        return entityManager.find(Security.class, secid);
    }
}
