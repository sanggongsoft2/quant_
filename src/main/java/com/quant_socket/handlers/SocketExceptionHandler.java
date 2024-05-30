package com.quant_socket.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class SocketExceptionHandler {

    @ExceptionHandler(Exception.class)
    public void handler(Exception e) {
        log.error("[Exception] cause: {}, message: {}", e.getCause(), e.getLocalizedMessage());
    }
}
