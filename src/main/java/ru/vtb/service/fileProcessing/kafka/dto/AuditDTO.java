package ru.vtb.service.fileProcessing.kafka.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuditDTO extends AbstractDto {

    @JsonFormat
    private String description;

    public AuditDTO(String description) {
        this.description = description;
    }
}
