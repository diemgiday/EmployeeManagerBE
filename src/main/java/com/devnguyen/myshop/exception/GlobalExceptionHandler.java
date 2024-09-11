package com.devnguyen.myshop.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import com.devnguyen.myshop.response.ResponseObject;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)

    public ResponseEntity<ResponseObject> handleGeneralException (Exception exception){
        
        return ResponseEntity.internalServerError().body(
            ResponseObject.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("error")
                .build()  
        );
    }
}