package com.folksdev.account.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/***
 * @RestControllerAdvice: Uygulama icerisinde throw ettigim tum exceptionlari yakalar ve
 * bir HTTP response uretir
 */
@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @NotNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NotNull HttpHeaders headers,
                                                                  @NotNull HttpStatus status,
                                                                  @NotNull WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error ->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /***
     * Vermis oldugumuz exception durumu olustugunda (customerNotFound), service Controller'a haber verir
     * Controller'da bu hatayi islemi yapar ve islem biter. Bu class'la birlikte RestControllerAdvice Service ve
     * Controller arasina girer. Ben bu hatayi taniyorum bu hatayi bana ver diyor. Bu sekilde bir HTTP response
     * olusturuyor ve kullanciya HTTP response donuyor.
     */
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> customerNotFoundExceptionHandler(CustomerNotFoundException exception)  {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
