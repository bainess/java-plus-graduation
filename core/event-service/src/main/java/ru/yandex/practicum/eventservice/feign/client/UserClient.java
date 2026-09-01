package ru.yandex.practicum.eventservice.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.explorewithme.shareddto.dto.user.UserShortDto;

@FeignClient(name = "main-service", contextId = "users")
public interface UserClient {
    @GetMapping("/admin/users/{userId}")
    UserShortDto findById(@PathVariable Long userId);
}
