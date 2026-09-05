package ru.yandex.practicum.eventservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import ru.practicum.explorewithme.shareddto.exception.ErrorHandler;

@SpringBootApplication(scanBasePackages = {
        "ru.yandex.practicum.eventservice"})
@EnableFeignClients
@Import(ErrorHandler.class)
public class EventServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(EventServiceApp.class, args);
    }
}
