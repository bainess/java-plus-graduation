package ru.practicum.explorewithme.stats.client.exception;

public class StatsServerUnavailable extends RuntimeException {
  public StatsServerUnavailable(String message , Exception e) {
    super(message);
  }
}
