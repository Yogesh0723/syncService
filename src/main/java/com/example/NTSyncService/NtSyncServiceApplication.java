package com.example.NTSyncService;

import com.example.NTSyncService.service.ConsumerRecordServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NtSyncServiceApplication implements CommandLineRunner {

	@Autowired
	private ConsumerRecordServiceInterface consumerRecordService; // Use the interface

	public static void main(String[] args) {
		SpringApplication.run(NtSyncServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		consumerRecordService.processJsonFile("src/main/resources/complete.json");
	}
}