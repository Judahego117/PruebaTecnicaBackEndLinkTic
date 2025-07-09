package com.productos.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex){
        Map<String, Object> error = new HashMap<>();
        error.put("status", 400);
        error.put("title", "Datos inválidos");

        Map<String, String> fields = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fe -> fields.put(fe.getField(), fe.getDefaultMessage()));

        error.put("errors", fields);

        return ResponseEntity.badRequest().body(Map.of("errors", error));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException ex) {
        return ResponseEntity.status(404).body(Map.of(
                "errors", Map.of(
                        "status", 404,
                        "title", "Recurso no encontrado",
                        "detail", ex.getMessage()
                )
        ));
    }
}
