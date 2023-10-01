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
 * @RestControllerAdvice: Uygulama icerisinde throw ettigim tum exceptionlari yakalar ve bir HTTP response uretir
 * Buna ek olarak Controller'da @Valid Assertionunu kullanabilirsam da hatayi yakalayan taraf yine RestControllerAdvice
 * olur.
 * @Valid: Endpointte bir validation surecimiz var. (avax.validation library) Ben controller'ima request icin
 * @Valid annotitaion'a koydugumda burada request'in validationu kontrol ediliyor. Eger @Valid'e aykiri bir durum
 * olursa devreye yine RestControllerAdvice girer. Isin guzelligi, eger bir validation sorunu varsa araya Controller
 * girmeden bu sorunu firlatir. Bu sekilde kod kalabaligindan kurtuluruz ve controller'in thread pool'una bu request
 * hic girmedigi icin performans acisindan da yararli olur.
 */
@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    /***
     * handleMethodArgumentNotValid functionu internal server error 500 doner. Bu deger kullanici icin anlamli
     * bir deger degil. Biz bu methodu override edicez. Bu sekilde anlamli bir exception donecegiz.
     * CreateAccountRequest'de gorebiliriz.
     */
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
     * Controller'da bu hata ile ilgili islem yapacaksa yapar ve islem biter. Bu class'la birlikte
     * RestControllerAdvice Service ve Controller arasina girer(interruption).
     * Ben bu hatayi taniyorum bu hatayi bana ver der. Bu sekilde bir HTTP response olusturuyor ve
     * kullanciya HTTP response donuyor.
     */
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> customerNotFoundExceptionHandler(CustomerNotFoundException exception)  {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
