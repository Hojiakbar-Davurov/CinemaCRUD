package com.example.cinemacrud.exeptions;

import com.example.cinemacrud.model.dto.ApiResponseWrapperDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNullApi;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class MyExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public HttpEntity<?> resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
        ApiResponseWrapperDTO apiResponseWrapperDTO = new ApiResponseWrapperDTO(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<Object>(apiResponseWrapperDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ResourceAlreadyExistsException.class)
    public HttpEntity<?> resourceAlreadyExistsExceptionHandler(ResourceAlreadyExistsException e) {
        ApiResponseWrapperDTO apiResponseWrapperDTO = new ApiResponseWrapperDTO(
                e.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponseWrapperDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = ResourceNotDeletedException.class)
    public HttpEntity<?> resourceNotDeletedExceptionHandler(ResourceNotDeletedException e) {
        ApiResponseWrapperDTO apiResponseWrapperDTO = new ApiResponseWrapperDTO(
                e.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponseWrapperDTO, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ValidErrorResponse error = new ValidErrorResponse("Validation Failed", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}

