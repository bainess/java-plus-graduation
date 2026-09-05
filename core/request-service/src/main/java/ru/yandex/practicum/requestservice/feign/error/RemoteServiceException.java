package ru.yandex.practicum.requestservice.feign.error;

public class RemoteServiceException extends RuntimeException {
  public RemoteServiceException(String message) {
    super(message);
  }
}
