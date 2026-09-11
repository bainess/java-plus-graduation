package ru.practicum.userservice.mapper;


import ru.practicum.userservice.dto.NewUserRequest;
import ru.practicum.userservice.dto.UserDto;
import ru.practicum.userservice.model.User;

public final class UserMapper {
    public static User toEntity(NewUserRequest request) {
        return new User(null, request.getEmail(), request.getName());
    }

    public static UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getName());
    }
}