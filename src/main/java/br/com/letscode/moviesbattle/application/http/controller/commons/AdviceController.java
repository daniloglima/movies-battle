package br.com.letscode.moviesbattle.application.http.controller.commons;

import br.com.letscode.moviesbattle.application.http.controller.facade.ResponseWrapped;
import br.com.letscode.moviesbattle.usecase.commons.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ResponseWrapped> validate(ApplicationException exception, HttpServletRequest request){

        var result = ResponseWrapped.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .timeStamp(System.currentTimeMillis())
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

}
