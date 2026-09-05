package ru.yandex.practicum.requestservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.explorewithme.shareddto.dto.user.UserShortDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@FeignClient(name = "main-service", contextId = "users")
public interface UserClient {
    @GetMapping("/admin/users/{userId}")
    UserShortDto findById(@PathVariable Long userId);

    @GetMapping("/api/users")
    Map<Long, UserShortDto> findAllById(@RequestParam List<Long> requesterIds);
}
