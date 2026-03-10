package com.example.taskapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public Object handleTaskNotFound(TaskNotFoundException ex,
                                     jakarta.servlet.http.HttpServletRequest request,
                                     Model model) {

        String uri = request.getRequestURI();

        if (uri != null && uri.startsWith("/api/")) {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("status", 404);
            body.put("error", "Not Found");
            body.put("message", ex.getMessage());
            body.put("path", uri);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        }

        model.addAttribute("message", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex,
                                                                     jakarta.servlet.http.HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", 400);
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}