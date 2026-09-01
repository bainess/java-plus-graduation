package ru.yandex.practicum.eventservice.feign.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.shareddto.exception.ErrorResponse;
import ru.practicum.explorewithme.shareddto.exception.NotFoundException;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventRequestExceptionDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String s, Response response) {
        try {
            ErrorResponse errorResponse = objectMapper.readValue(
                    response.body().asInputStream(),
                    ErrorResponse.class
            );

            log.error(
                    "Remote service call failed: method={}, status={}, url={}, message={}",
                    s,
                    response.status(),
                    response.request().url(),
                    errorResponse.message()
            );

            if (response.status() == 404) {
                return new NotFoundException(errorResponse.message());
            }

            return new RemoteServiceException(errorResponse.message());

        } catch (Exception e) {
            log.error(
                    "Failed to parse error response: method={}, status={}, url={}",
                    s,
                    response.status(),
                    response.request().url(),
                    e
            );

            return new RemoteServiceException(
                    "Remote service error: " + response.status()
            );
        }

    }
}
