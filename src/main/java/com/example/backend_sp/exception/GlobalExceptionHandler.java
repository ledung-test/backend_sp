package com.example.backend_sp.exception;

import com.example.backend_sp.response.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseObject> handlerException(Exception e){
        return ResponseEntity.internalServerError().body(
                ResponseObject.builder()
                        .message(e.getMessage())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .build()
        );
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseObject> handlerValidation(MethodArgumentNotValidException e){
        return ResponseEntity.badRequest().body(
                ResponseObject.builder()
                        .message(Objects.requireNonNull(e.getFieldError()).getDefaultMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .build()
        );
    }
}
