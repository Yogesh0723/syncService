package com.example.NTSyncService.service;

import java.io.IOException;
import java.util.List;

public interface ConsumerRecordServiceInterface {
    void processJsonFile(String jsonRecord) throws IOException; // Change here
    List<String> extractStdSyncUUIDs(String jsonContent);
}