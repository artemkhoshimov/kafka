package ru.vtb.service.fileProcessing.kafka.service;

import ru.vtb.service.fileProcessing.kafka.dto.AuditDTO;

public interface AuditService {

    public void send(AuditDTO auditDTO);

    public void consume(AuditDTO auditDTO);

}
