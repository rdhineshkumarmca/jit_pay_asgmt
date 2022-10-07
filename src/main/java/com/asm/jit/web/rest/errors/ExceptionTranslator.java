package com.asm.jit.web.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionTranslator {

    @ExceptionHandler(BadRequestAlertException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public FieldErrorVM handleBadRequestAlertException(BadRequestAlertException ex) {
        FieldErrorVM fieldErrorVM = new FieldErrorVM();
        fieldErrorVM.setMessage(ex.getMessage());
        return fieldErrorVM;
    }
    
}
