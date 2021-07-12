package com.test.task.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class JobConfig {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public BatchConfigSecurity batchConfigSecurity;

    @Autowired
    public  BatchConfigHistory batchConfigHistory;

    @Bean
    public Job securityJob() throws IOException {
        return jobBuilderFactory
                .get("securityJob")
                .incrementer(new RunIdIncrementer())
                .flow(batchConfigSecurity.securityStep())
                .next(batchConfigHistory.historyStep())
                .end()
                .build();
    }
}
