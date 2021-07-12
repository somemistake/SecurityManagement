package com.test.task.processor;

import com.test.task.model.Security;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class SecurityItemProcessor implements ItemProcessor<Security, Security> {
    @Override
    public Security process(Security security) throws Exception {
        return security;
    }
}
