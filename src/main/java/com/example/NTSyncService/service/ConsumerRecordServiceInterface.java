package com.example.NTSyncService.service;

import java.io.IOException;
import java.util.List;

public interface ConsumerRecordServiceInterface {
    void processJsonFile(String filePath) throws IOException;
    List<String> extractStdSyncUUIDs(String jsonContent);
}