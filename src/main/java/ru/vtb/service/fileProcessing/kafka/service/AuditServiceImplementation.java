package ru.vtb.service.fileProcessing.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.vtb.service.fileProcessing.kafka.dto.AuditDTO;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class AuditServiceImplementation implements AuditService {


    private final KafkaTemplate<Long, AuditDTO> kafkaStarshipTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public AuditServiceImplementation(KafkaTemplate<Long, AuditDTO> kafkaStarshipTemplate,
                                      ObjectMapper objectMapper) {
        this.kafkaStarshipTemplate = kafkaStarshipTemplate;
        this.objectMapper = objectMapper;
    }


    @Override
    public void send(AuditDTO auditDTO) {
        kafkaStarshipTemplate.send("server.starship", auditDTO);
    }

    @Override
    @KafkaListener(id = "Starship", topics = {"server.starship"}, containerFactory = "singleFactory")
    public void consume(AuditDTO auditDTO) {
        log.info("=> consumed {}", writeValueAsString(auditDTO));
    }

    private String writeValueAsString(AuditDTO dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Writing value to JSON failed: " + dto.toString());
        }
    }
}
