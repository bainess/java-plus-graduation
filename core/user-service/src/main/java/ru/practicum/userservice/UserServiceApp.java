package ru.practicum.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import ru.practicum.explorewithme.shareddto.exception.ErrorHandler;

@SpringBootApplication(scanBasePackages = {"ru.practicum.userservice"})
@EnableFeignClients
@Import(ErrorHandler.class)
public class UserServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApp.class, args);
    }
}
