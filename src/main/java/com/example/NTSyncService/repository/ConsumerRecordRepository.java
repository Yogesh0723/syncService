package com.example.NTSyncService.repository;

import com.example.NTSyncService.entity.ConsumerRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumerRecordRepository extends JpaRepository<ConsumerRecord, Long> {
    boolean existsByStdSyncUUID(String stdSyncUUID);
}