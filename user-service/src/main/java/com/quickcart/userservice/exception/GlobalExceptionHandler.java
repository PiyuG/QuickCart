package com.quickcart.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Map<String,Object>> handleExists(ResourceAlreadyExistsException ex){
        Map<String,Object> m=Map.of("error","Conflict","message",ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(m);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleNotFound(ResourceNotFoundException ex){
        Map<String,Object> m=Map.of("error","Not Found",",message",ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(m);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleValidation(MethodArgumentNotValidException ex){
        Map<String,Object> error=new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err-> error.put(err.getField(),err.getDefaultMessage()));
        Map<String, Object> body=Map.of("error","validation failed","details",error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleAll(Exception ex){
        Map<String,Object> m=Map.of("error","Internal Server Error","Message",ex.getMessage());
        return ResponseEntity.status((HttpStatus.INTERNAL_SERVER_ERROR)).body(m);
    }
}
