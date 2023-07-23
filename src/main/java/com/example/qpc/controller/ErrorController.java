package com.example.qpc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;

@Controller
@ControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {

    @GetMapping("/error/401")
    public String error401Handler() {
        return "/errorPages/error401";
    }

    @GetMapping("/error/403")
    public String error403Handler() {
        return "/errorPages/error403";
    }

    @GetMapping("/error/405")
    public String error405Handler() {
        return "/errorPages/error405";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>("권한이 없습니다.", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
        return new ResponseEntity<>("인증되지 않았습니다.", HttpStatus.UNAUTHORIZED);
    }

}
