package org.example.smartchef.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorController {

    //404 NOT FOUND
    @ExceptionHandler(ElementoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> manejarElementoNoEncontrado(ElementoNoEncontradoException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    //400 BAD REQUEST
    @ExceptionHandler(ValidacionDeNegocioException.class)
    public ResponseEntity<Object> handleValidacionDeNegocio(ValidacionDeNegocioException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Error de Validación de Negocio");
        response.put("mensaje", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> controladorErrores(MethodArgumentNotValidException exception) {

        Map<String, String> mapaErrores= new HashMap<>();

        for (FieldError error : exception.getBindingResult().getFieldErrors()) {

            mapaErrores.put(error.getField(), error.getDefaultMessage());

        }


        return new ResponseEntity<>(mapaErrores, HttpStatus.BAD_REQUEST);
    }
}
