package com.example.NTSyncService.repository;

import com.example.NTSyncService.entity.ConsumerRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsumerRecordRepository extends JpaRepository<ConsumerRecord, Long> {
    boolean existsByStdSyncUUID(String stdSyncUUID);
    List<ConsumerRecord> findByStatus(String status);
}