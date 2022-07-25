package ru.vtb.service.fileProcessing.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
@Slf4j
public class AuditServiceImplementation implements AuditService {


    private final KafkaTemplate<Long, Object> kafkaAuditTemplate;
    private final ObjectMapper objectMapper;

    @Value("${path.csv.file}")
    private  String pathToCSVFile;

    @Autowired
    public AuditServiceImplementation(KafkaTemplate<Long, Object> kafkaAuditTemplate,
                                      ObjectMapper objectMapper) {
        this.kafkaAuditTemplate = kafkaAuditTemplate;
        this.objectMapper = objectMapper;
    }


    @Override
    public void send(Object auditDTO) {
        kafkaAuditTemplate.send("server.audit", auditDTO);
    }

    @Override
    @KafkaListener(id = "Audit", topics = {"audit"}, containerFactory = "singleFactory")
    public void consume(Object auditDTO) {
        log.info("=> consumed {}", auditDTO.toString());
    }

    private byte[] writeValueAsString(Object dto) {
        try {
            return objectMapper.writeValueAsBytes(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Writing value to JSON failed: " + dto.toString());
        }
    }

    @Scheduled(initialDelayString = "${kafka.send.initialDelay}", fixedDelayString = "${kafka.send.delay}")
    @Override
    public void produce() {
        Object dto = createDto();
        kafkaAuditTemplate.send("audit", dto);
    }



    private Object createDto() {
        String filePath = pathToCSVFile;
        List<String[]> r = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
             r = reader.readAll();
             r.forEach(x -> log.info(Arrays.toString(x)));
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return r;
    }
}
