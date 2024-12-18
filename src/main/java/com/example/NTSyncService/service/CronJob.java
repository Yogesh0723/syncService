package com.example.NTSyncService.service;


import com.example.NTSyncService.entity.ConsumerRecord;
import com.example.NTSyncService.repository.ConsumerRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CronJob {
    private static final Logger logger = LoggerFactory.getLogger(CronJob.class);

    @Autowired
    private ConsumerRecordRepository repository;

    /**
     * Scheduled method to delete old records with status "VALIDATED".
     * This method runs every 10 minutes.
     */
    @Scheduled(fixedRate = 600000) // 10 minutes in milliseconds
    public void deleteOldValidatedRecords() {
        List<ConsumerRecord> validatedRecords = repository.findByStatus("VALIDATED");

        if (!validatedRecords.isEmpty()) {
            repository.deleteAll(validatedRecords);
            logger.info("Deleted {} validated records from the database.", validatedRecords.size());
        } else {
            logger.info("No validated records found to delete.");
        }
    }
}