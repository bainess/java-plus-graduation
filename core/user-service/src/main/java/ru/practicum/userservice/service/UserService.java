package ru.practicum.userservice.service;

import ru.practicum.explorewithme.shareddto.dto.user.UserShortDto;
import ru.practicum.userservice.dto.NewUserRequest;
import ru.practicum.userservice.dto.UserDto;

import java.util.List;
import java.util.Map;

public interface UserService {
    UserDto registerUser(NewUserRequest newUserRequest);

    List<UserDto> getUsers(List<Long> ids, int from, int size);

    void deleteUser(Long userId);

    UserShortDto getUserShortDto(Long userId);

    Map<Long, UserShortDto> getUsersByIds(List<Long> userIds);
}