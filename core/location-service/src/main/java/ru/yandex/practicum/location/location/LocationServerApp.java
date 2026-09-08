package ru.yandex.practicum.location.location;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import ru.practicum.explorewithme.shareddto.exception.ErrorHandler;

@SpringBootApplication(scanBasePackages = "ru.yandex.practicum.location")
@EnableFeignClients
@Import(ErrorHandler.class)
public class LocationServerApp {
    public static void main(String[] args) {
        SpringApplication.run(LocationServerApp.class, args);
    }

}
