package ru.practicum.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.shareddto.dto.user.UserShortDto;
import ru.practicum.userservice.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {
    private final UserService userService;

    @GetMapping
    public Map<Long, UserShortDto> findAllById(@RequestParam List<Long> requesterIds) {
        return userService.getUsersByIds(requesterIds);
    }
}
