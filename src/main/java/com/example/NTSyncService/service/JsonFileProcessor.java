package com.example.NTSyncService.service;

import java.io.IOException;

public interface JsonFileProcessor {
    void processFile(String filePath) throws IOException;
}