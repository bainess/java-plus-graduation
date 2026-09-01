package ru.practicum.explorewithme.service.request.feign.error;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventSerivceErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new RemoteServiceException("Ресурс соседнего сервиса не найден");
        }

        if (response.status() == 409) {
            return new RemoteServiceException("Операция не может быть выполнена");
        }

        return defaultDecoder.decode(methodKey, response);
    }
}
