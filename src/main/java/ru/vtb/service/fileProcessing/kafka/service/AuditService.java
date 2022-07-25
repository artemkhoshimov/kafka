package ru.vtb.service.fileProcessing.kafka.service;

import ru.vtb.service.fileProcessing.kafka.dto.AuditDTO;

public interface AuditService {

    public void send(Object object);

    public void consume(Object auditDTO);

    public void produce();

}
