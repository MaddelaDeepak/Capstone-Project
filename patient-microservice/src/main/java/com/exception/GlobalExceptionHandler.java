package com.exception;

import jakarta.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler 
{
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) 
	{
		List<String> details = new ArrayList<>();
		ex.getConstraintViolations().forEach(violation -> {
			String propertyPath = violation.getPropertyPath().toString();
			String propertyName = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
			String errorMessage = violation.getMessage();
			details.add(propertyName + " | " + errorMessage);
		 
		});
		ErrorResponse error = new ErrorResponse("Validation Failed", details);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CustomException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<?> handleCustomException(CustomException ex)
	{
		List<String> details = new ArrayList<>();
		details.add("cause: "+ex.getMessage());
		ErrorResponse error = new ErrorResponse("Data Not Available", details);
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);		
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) 
    {
        List<String> details = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            details.add(error.getField() + ": " + error.getDefaultMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse("Validation Failed", details);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        return new ResponseEntity<>(ex.getReason(), ex.getStatusCode());
    }


}
