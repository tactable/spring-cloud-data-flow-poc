package com.example.tasks;

import com.example.entity.Person;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import java.io.File;
import java.util.List;

public class JsonWriter implements ItemWriter<Person> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(JsonWriter.class);



    @Override
    public void write(Chunk<? extends Person> chunk) throws Exception {
        List<? extends Person> items = chunk.getItems();
        File outputFile = new File("/opt/output/output.json");
        objectMapper.writeValue(outputFile, items);
        if (outputFile.exists()) {
            logger.info("✅ JSON File Created Successfully: {}", outputFile.getAbsolutePath());
        } else {
            logger.error("❌ Failed to create JSON file: {}", outputFile.getAbsolutePath());
        }
    }
}

