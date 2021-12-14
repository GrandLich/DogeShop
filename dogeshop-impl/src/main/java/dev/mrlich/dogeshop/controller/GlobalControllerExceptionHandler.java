package dev.mrlich.dogeshop.controller;

import dev.mrlich.dogeshop.api.dto.response.MessageResponse;
import dev.mrlich.dogeshop.api.exception.ActionIsNotAllowedException;
import dev.mrlich.dogeshop.api.exception.AuthenticationException;
import dev.mrlich.dogeshop.api.exception.EntityNotFoundException;
import dev.mrlich.dogeshop.api.exception.NotEnoughMoneyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(ActionIsNotAllowedException.class)
    public ResponseEntity<?> handleActionIsNotAllowedException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<MessageResponse> handleAuthenticationException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new MessageResponse().message("Login or password incorrect"));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<MessageResponse> handleEntityNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MessageResponse().message("Entity not found"));
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    public ResponseEntity<MessageResponse> handleNotEnoughMoneyException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new MessageResponse().message("Not enough money"));
    }

}
