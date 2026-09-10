package ru.yandex.practicum.requestservice.feign.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.shareddto.dto.user.UserShortDto;
import ru.practicum.explorewithme.shareddto.exception.UserServiceUnavailableException;
import ru.yandex.practicum.requestservice.feign.UserClient;

import java.util.List;
import java.util.Map;
@Slf4j
@Component
public class UserClientFallback implements FallbackFactory<UserClient> {
    @Override
    public UserClient create(Throwable cause) {
        return new UserClient() {
            @Override
            public UserShortDto findById(Long userId) {
                log.warn("FALLBACK: User service is not available for user id={}", userId, cause);
                throw new UserServiceUnavailableException(userId, cause);
            }

            @Override
            public Map<Long, UserShortDto> findAllById(List<Long> requesterIds) {
                log.warn("FALLBACK: User service is not available for user id={}", requesterIds, cause);
                throw new UserServiceUnavailableException(requesterIds.toString(), cause);
            }
        };
    }
}
