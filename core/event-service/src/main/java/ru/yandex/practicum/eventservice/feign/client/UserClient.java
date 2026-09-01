package ru.yandex.practicum.eventservice.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.eventservice.feign.model.UserResponse;

@FeignClient(name = "ewm-main-service")
public interface UserClient {
    @GetMapping("/admin/users")
    UserResponse findById(@RequestParam Long userId);
}
