package com.test.task.config;

import com.test.task.model.Security;
import com.test.task.processor.SecurityItemProcessor;
import com.test.task.writer.SecurityItemWriter;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.oxm.xstream.XStreamMarshaller;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class BatchConfigSecurity {
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Autowired
    public SecurityItemWriter securityItemWriter;

    @Autowired
    public SecurityItemProcessor securityItemProcessor;

    private static class SecurityConverter implements Converter {
        @Override
        public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {

        }

        @Override
        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
            Long id = Long.parseLong(reader.getAttribute("id"));
            String secid = reader.getAttribute("secid");
            String regnumber = reader.getAttribute("regnumber");
            String name = reader.getAttribute("name");
            String emitentTitle = reader.getAttribute("emitent_title");
            Security security = new Security();
            security.setId(id);
            security.setSecid(secid);
            security.setRegnumber(regnumber);
            security.setName(name);
            security.setEmitentTitle(emitentTitle);
            return security;
        }

        @Override
        public boolean canConvert(Class type) {
            return type.equals(Security.class);
        }
    }

    @Bean
    public Resource[] securityResources() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        return resolver.getResources("/import/securities_*.xml");
    }

    @Bean
    public MultiResourceItemReader<Security> securityMultiReader() throws IOException {
        MultiResourceItemReader<Security> multiReader = new MultiResourceItemReader<>();
        multiReader.setResources(securityResources());
        multiReader.setDelegate(securityReader());
        return multiReader;
    }

    @Bean
    public StaxEventItemReader<Security> securityReader() {
        StaxEventItemReader<Security> reader = new StaxEventItemReader<>();
        reader.setFragmentRootElementName("row");
        Map<String, Class<Security>> aliases = new HashMap<>();
        aliases.put("row", Security.class);
        XStreamMarshaller marshaller = new XStreamMarshaller();
        marshaller.setAliases(aliases);
        marshaller.setConverters(new SecurityConverter());
        marshaller.setNameCoder(new NoNameCoder());
        reader.setUnmarshaller(marshaller);
        return reader;
    }

    @Bean
    public Step securityStep() throws IOException {
        return stepBuilderFactory
                .get("securityStep")
                .<Security, Security> chunk(100)
                .reader(securityMultiReader())
                .processor(securityItemProcessor)
                .writer(securityItemWriter)
                .build();
    }
}
