package ru.vtb.service.fileProcessing.kafka.dto;


public class AuditDTO {
    private String id;
    private String value;

    public AuditDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
