package ru.vtb.service.fileProcessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


@SpringBootApplication
public class FileProcessingMain {


    public static void main(String[] args) {
        SpringApplication.run(FileProcessingMain.class);
    }

    private static void doWriteFile() {
        Path path = Paths.get("src/main/resources/auditReport/auditReport.csv");
        byte[] data = "Бинарные данные ".getBytes();
        try {
            Files.write(path, data, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
