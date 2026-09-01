package ru.yandex.practicum.eventservice.feign.error;

public class RemoteServiceException extends RuntimeException {
  public RemoteServiceException(String message) {
    super(message);
  }
}
