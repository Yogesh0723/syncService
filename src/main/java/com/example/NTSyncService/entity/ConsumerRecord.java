package com.example.NTSyncService.entity;

import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Data
@Entity(name = "NT_AUDIT_LOGS") // Set the table name here
public class ConsumerRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stdSyncUUID;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStdSyncUUID() {
        return stdSyncUUID;
    }

    public void setStdSyncUUID(String stdSyncUUID) {
        this.stdSyncUUID = stdSyncUUID;
    }
}