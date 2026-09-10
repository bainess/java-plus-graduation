package ru.practicum.explorewithme.shareddto.exception;

public class UserServiceUnavailableException  extends RuntimeException{
    public UserServiceUnavailableException(Long num, String message) {
        super(message);
    }
    public UserServiceUnavailableException(Long aLong, Throwable cause) {

    }
    public UserServiceUnavailableException(String a, Throwable cause) {

    }
}
