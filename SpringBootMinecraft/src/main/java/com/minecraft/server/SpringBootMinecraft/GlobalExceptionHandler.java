package com.minecraft.server.SpringBootMinecraft;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Manejar excepciones de tipo RuntimeException
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Puedes cambiar el status según el error
    public String handleRuntimeException(RuntimeException e) {
        // Log de la excepción
        logger.error("Ocurrió un error: ", e);
        return "Hubo un error en la aplicación: " + e.getMessage();
    }

    // Manejar excepciones genericas
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e) {
        logger.error("Error general: ", e);
        return "Error en la aplicación: " + e.getMessage();
    }
}
