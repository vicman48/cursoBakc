package com.api.rest.apirestencuestas.exception.handler;

import com.api.rest.apirestencuestas.dto.ErrorDetail;
import com.api.rest.apirestencuestas.dto.ValidationError;
import com.api.rest.apirestencuestas.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handlerResourceNotFoundException(ResourceNotFoundException exception, HttpServletRequest httpServletRequest){
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle("Recursos no encontrados");
        errorDetail.setDetail(exception.getClass().getName());
        errorDetail.setDeveloperMessage(exception.getMessage());
        return new ResponseEntity<>(errorDetail,null,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,HttpServletRequest httpServletRequest){
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());

        String requestPath = (String) httpServletRequest.getAttribute("javax.servelet.error.request_uri");

        if(requestPath == null){
            requestPath = httpServletRequest.getRequestURI();
        }
        errorDetail.setTitle("Validacion fallida");
        errorDetail.setDetail("La validacion de entrada fallo");
        errorDetail.setDeveloperMessage(exception.getMessage());

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        for (FieldError fieldError:fieldErrors){
            List<ValidationError> validationErrorsList = errorDetail.getError().get(fieldError.getField());

            if(validationErrorsList == null){
                validationErrorsList = new ArrayList<ValidationError>();
                errorDetail.getError().put(fieldError.getField(),validationErrorsList);
            }
            ValidationError validationError = new ValidationError();
            validationError.setCode(fieldError.getCode());
            validationError.setMessage(fieldError.getDefaultMessage());
            validationErrorsList.add(validationError);
        }
        return new ResponseEntity<>(errorDetail,null,HttpStatus.BAD_REQUEST);
    }
}
