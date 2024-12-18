package com.example.NTSyncService.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
public class JsonFileProcessorImpl implements JsonFileProcessor {

    private static final Logger logger = LoggerFactory.getLogger(JsonFileProcessorImpl.class);

    @Autowired
    private ConsumerRecordServiceInterface consumerRecordService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Processes a JSON file located at the specified file path.
     *
     * @param filePath the path to the JSON file to be processed
     * @throws IOException if there is an error reading the file or parsing the JSON
     */
    @Override
    public void processFile(String filePath) throws IOException {
        logger.info("Starting to process file: {}", filePath);
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                jsonContent.append(line);
            }
            JsonNode rootNode = objectMapper.readTree(jsonContent.toString());
            JsonNode latestArray = rootNode.path("CONSUMER_NAMETABLE").path("latest");

            for (JsonNode record : latestArray) {
                String jsonRecord = record.toString();
                consumerRecordService.processJsonFile(jsonRecord);
                logger.debug("Processed JSON record: {}", jsonRecord); // Log each processed record
            }
        } catch (IOException e) {
            logger.error("Error processing file {}: {}", filePath, e.getMessage(), e); // Log the error with details
            throw e; // Rethrow the exception after logging
        }
        logger.info("Finished processing file: {}", filePath);
    }
}