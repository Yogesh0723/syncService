package com.example.NTSyncService.service;

import com.example.NTSyncService.entity.ConsumerRecord;
import com.example.NTSyncService.repository.ConsumerRecordRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConsumerRecordService implements ConsumerRecordServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerRecordService.class);

    @Autowired
    private ConsumerRecordRepository repository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Processes an individual JSON record and saves it to the database.
     *
     * @param jsonRecord the JSON string representing a consumer record
     * @throws IOException if there is an error reading the JSON
     */
    @Override
    public void processJsonFile(String jsonRecord) throws IOException {
        // Process the individual JSON record
        JsonNode recordNode = objectMapper.readTree(jsonRecord);
        String stdSyncUUID = recordNode.path("standard").path("StdsyncUUID").asText();

        if (!stdSyncUUID.isEmpty()) {
            ConsumerRecord record = new ConsumerRecord();
            record.setStdSyncUUID(stdSyncUUID);
            record.setStatus("VALIDATED"); // Use the constant for status
            repository.save(record);
            logger.info("Saved record with StdSyncUUID: {}", stdSyncUUID); // Log the saved record
        } else {
            logger.warn("No StdSyncUUID found in the record: {}", jsonRecord); // Log a warning if UUID is empty
        }
    }

    /**
     * Extracts StdSyncUUIDs from the provided JSON content.
     *
     * @param jsonContent the JSON string containing multiple consumer records
     * @return a list of StdSyncUUIDs extracted from the JSON content
     */
    @Override
    public List<String> extractStdSyncUUIDs(String jsonContent) {
        List<String> stdSyncUUIDs = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(jsonContent);
            JsonNode latestArray = rootNode.path("CONSUMER_NAMETABLE").path("latest");

            for (JsonNode record : latestArray) {
                JsonNode standardNode = record.path("standard");
                String stdSyncUUID = standardNode.path("StdsyncUUID").asText();
                if (!stdSyncUUID.isEmpty()) {
                    stdSyncUUIDs.add(stdSyncUUID);
                    logger.debug("Extracted StdSyncUUID: {}", stdSyncUUID); // Log the extracted UUID
                } else {
                    logger.warn("Empty StdSyncUUID found in record: {}", record); // Log a warning for empty UUID
                }
            }
        } catch (IOException e) {
            logger.error("Error processing JSON content: {}", e.getMessage(), e); // Log the error
        }
        return stdSyncUUIDs;
    }
}