package com.example.config;

import com.example.entity.Person;
import com.example.tasks.CsvReader;
import com.example.tasks.JsonWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableTask
public class BatchConfig {
    private static final Logger logger = LoggerFactory.getLogger(BatchConfig.class);

    @Bean
    public CommandLineRunner taskRunner() {
        return args -> {
            logger.info("✅ Task started: Processing CSV files...");
            List<Person> allPersons = new ArrayList<>();

            try {
                allPersons.addAll(readCsv("/opt/data/source1/source1.csv"));
                allPersons.addAll(readCsv("/opt/data/source2/source2.csv"));

                new JsonWriter().write(new Chunk<>(allPersons));
                logger.info("✅ Task completed successfully!");
            } catch (Exception e) {
                logger.error("❌ Task failed: {}", e.getMessage(), e);
            }
        };
    }

    private List<Person> readCsv(String filePath) throws Exception {
        FlatFileItemReader<Person> reader = CsvReader.getReader(filePath);
        reader.open(new ExecutionContext()); // Required to start reading
        List<Person> records = new ArrayList<>();
        Person person;

        while ((person = reader.read()) != null) {
            records.add(person);
        }

        reader.close();
        logger.info("✅ Successfully read {} records from {}", records.size(), filePath);
        return records;
    }

}