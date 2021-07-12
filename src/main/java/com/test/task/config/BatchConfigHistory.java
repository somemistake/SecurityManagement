package com.test.task.config;

import com.test.task.model.History;
import com.test.task.model.Security;
import com.test.task.processor.HistoryItemProcessor;
import com.test.task.writer.HistoryItemWriter;
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
import org.springframework.oxm.UnmarshallingFailureException;
import org.springframework.oxm.xstream.XStreamMarshaller;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
@EnableBatchProcessing
public class BatchConfigHistory {
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Autowired
    public HistoryItemWriter historyItemWriter;

    @Autowired
    public HistoryItemProcessor historyItemProcessor;

    private static class HistoryConverter implements Converter {
        private static final AtomicLong id = new AtomicLong();

        @Override
        public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {

        }

        @Override
        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
            String secid = reader.getAttribute("SECID");
            Date tradedate = Date.valueOf(reader.getAttribute("TRADEDATE"));
            Integer numtrades = Integer.parseInt(reader.getAttribute("NUMTRADES"));
            String open = reader.getAttribute("OPEN");
            String close = reader.getAttribute("CLOSE");
            History history = new History();
            history.setId(id.incrementAndGet());
            Security security = new Security();
            security.setSecid(secid);
            history.setSecurity(security);
            history.setTradedate(tradedate);
            history.setNumtrades(numtrades);
            if (open.isEmpty()) history.setOpen(0d);
            else history.setOpen(Double.parseDouble(open));
            if (close.isEmpty()) history.setClose(0d);
            else history.setClose(Double.parseDouble(close));
            return history;
        }

        @Override
        public boolean canConvert(Class type) {
            return type.equals(History.class);
        }
    }

    @Bean
    public Resource[] historyResources() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        return resolver.getResources("/import/history_*.xml");
    }

    @Bean
    public MultiResourceItemReader<History> historyMultiReader() throws IOException {
        MultiResourceItemReader<History> multiReader = new MultiResourceItemReader<>();
        multiReader.setResources(historyResources());
        multiReader.setDelegate(historyReader());
        return multiReader;
    }

    @Bean
    public StaxEventItemReader<History> historyReader() {
        StaxEventItemReader<History> reader = new StaxEventItemReader<>();
        reader.setFragmentRootElementName("row");
        Map<String, Class<History>> aliases = new HashMap<>();
        aliases.put("row", History.class);
        XStreamMarshaller marshaller = new XStreamMarshaller();
        marshaller.setAliases(aliases);
        marshaller.setConverters(new HistoryConverter());
        marshaller.setNameCoder(new NoNameCoder());
        reader.setUnmarshaller(marshaller);
        return reader;
    }

    @Bean
    public Step historyStep() throws IOException {
        return stepBuilderFactory
                .get("historyStep")
                .<History, History> chunk(100)
                .reader(historyMultiReader())
                .processor(historyItemProcessor)
                .writer(historyItemWriter)
                .faultTolerant()
                .skipLimit(10)
                .skip(UnmarshallingFailureException.class)
                .build();
    }
}
