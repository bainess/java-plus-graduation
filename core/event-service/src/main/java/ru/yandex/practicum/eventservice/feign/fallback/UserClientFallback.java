package ru.yandex.practicum.eventservice.feign.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.shareddto.dto.user.UserShortDto;
import ru.practicum.explorewithme.shareddto.exception.UserServiceUnavailableException;
import ru.yandex.practicum.eventservice.feign.client.UserClient;

@Component
public class UserClientFallback implements FallbackFactory<UserClient> {
    private static final Logger log = LoggerFactory.getLogger(UserClientFallback.class);


    @Override
    public UserClient create(Throwable cause) {
        return new UserClient() {
            @Override
            public UserShortDto findById(Long userId) {
                log.warn("FALLBACK: User service is not available for user id={}", userId, cause);
                throw new UserServiceUnavailableException(userId, cause);
            }
        };
    }
}
