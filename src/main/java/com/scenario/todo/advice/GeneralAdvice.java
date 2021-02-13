package com.scenario.todo.advice;

import com.scenario.todo.exception.TodoNotFoundException;
import com.scenario.todo.rest_response.RestResponse;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GeneralAdvice {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<RestResponse<Void>> todoNotFoundException(TodoNotFoundException exc, WebRequest request){
        RestResponse<Void> restResponse = RestResponse.<Void>builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message("TodoNotFound")
                .build();
        return ResponseEntity.status(restResponse.getStatus()).body(restResponse);
    }


    @ExceptionHandler(BindException.class)
    public ResponseEntity<RestResponse<Void>> bindException(BindException exc, WebRequest request){

        Map<String,String> errors = new LinkedHashMap<>();
        exc.getFieldErrors().forEach(fieldError -> {
            if (fieldError.isBindingFailure()) {
                errors.put(fieldError.getField(),"Invalid Type");
                return;
            }
            errors.put(fieldError.getField(),fieldError.getDefaultMessage());
        });

        RestResponse<Void> restResponse = RestResponse.<Void>builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message("BadRequest")
                .errors(errors)
                .build();

        return ResponseEntity.status(restResponse.getStatus()).body(restResponse);
    }


}
