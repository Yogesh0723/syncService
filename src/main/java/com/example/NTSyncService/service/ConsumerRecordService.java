package com.example.NTSyncService.service;

import com.example.NTSyncService.entity.ConsumerRecord;
import com.example.NTSyncService.repository.ConsumerRecordRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConsumerRecordService implements ConsumerRecordServiceInterface {

    @Autowired
    private ConsumerRecordRepository repository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void processJsonFile(String filePath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        List<String> stdSyncUUIDs = extractStdSyncUUIDs(content);

        stdSyncUUIDs.forEach(stdSyncUUID -> {
            if (!repository.existsByStdSyncUUID(stdSyncUUID)) {
                ConsumerRecord record = new ConsumerRecord();
                record.setStdSyncUUID(stdSyncUUID);
                record.setStatus("VALIDATED");
                System.out.println(record);
                repository.save(record);
            }
        });
    }

    @Override
    public List<String> extractStdSyncUUIDs(String jsonContent) {
        List<String> stdSyncUUIDs = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(jsonContent);
            JsonNode latestArray = rootNode.path("CONSUMER_NAMETABLE").path("latest");

            for (JsonNode record : latestArray) {
                JsonNode standardNode = record.path("standard");
                String stdSyncUUID = standardNode.path("StdsyncUUID").asText();
                System.out.println(stdSyncUUID);
                if (!stdSyncUUID.isEmpty()) {
                    stdSyncUUIDs.add(stdSyncUUID);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
        return stdSyncUUIDs;
    }

    @Scheduled(cron = "0 0 * * * ?") // Example cron expression for daily execution
    public void deleteOldRecords() {
        // Implement logic to delete old records
    }
}