package com.test.task.writer;

import com.test.task.model.Security;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class SecurityItemWriter implements ItemWriter<Security> {
    @Autowired
    private EntityManager entityManager;

    @Override
    public void write(List<? extends Security> list) throws Exception {
        list.forEach(security -> {
            if (isSecurityExists(security))
                entityManager.merge(security);
            else
                entityManager.persist(security);
        });
    }

    private boolean isSecurityExists(Security security) {
        return entityManager.find(Security.class, security.getSecid()) != null;
    }
}
