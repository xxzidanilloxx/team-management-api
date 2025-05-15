package br.com.xxzidanilloxx.teammanagementapi.infra;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record RestErrorMessage(
        Integer errorCode,
        HttpStatus httpStatus,
        String error,
        String message,
        LocalDateTime timestamp,
        String path){

        public RestErrorMessage(Integer errorCode, HttpStatus httpStatus, String error, String message, String path) {
            this(errorCode, httpStatus, error, message, LocalDateTime.now(), path);
        }
}
