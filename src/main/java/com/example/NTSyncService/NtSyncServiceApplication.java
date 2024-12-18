package com.example.NTSyncService;

import com.example.NTSyncService.service.JsonFileProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NtSyncServiceApplication implements CommandLineRunner {

	@Autowired
	private JsonFileProcessor jsonFileProcessor; // Use the new processor

	public static void main(String[] args) {
		SpringApplication.run(NtSyncServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		jsonFileProcessor.processFile("src/main/resources/complete.json"); // Process the file
	}
}