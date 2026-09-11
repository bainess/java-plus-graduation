package ru.practicum.explorewithme.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import ru.practicum.explorewithme.shareddto.exception.ErrorHandler;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = "ru.practicum.explorewithme")
@Import(ErrorHandler.class)
public class MainServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(MainServiceApp.class, args);

    }
}
