package com.example;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;


@SpringBootApplication
@EnableTask
@EnableBatchProcessing
public class BatchProcessingApplication {
    public static void main(String[] args) {
        SpringApplication.run(BatchProcessingApplication.class, args);
    }
}
