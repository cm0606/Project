package com.incture.project.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;



@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException ex, WebRequest request){
		Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", ex.getMessage());
        errorDetails.put("status", HttpStatus.CONFLICT.toString());
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
		
	}
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundException(UserAlreadyExistsException ex, WebRequest request){
		Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", ex.getMessage());
        errorDetails.put("status", HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
		
	}
	@ExceptionHandler(UserNotAuthorisedException.class)
	public ResponseEntity<Object> handleUserNotAuthorisedException(UserNotAuthorisedException ex, WebRequest request){
		Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", ex.getMessage());
        errorDetails.put("status", HttpStatus.UNAUTHORIZED.toString());
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
		
	}
	@ExceptionHandler(TaskNotFoundException.class)
	public ResponseEntity<Object> handleTaskNotFoundException(TaskNotFoundException ex, WebRequest request){
		Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", ex.getMessage());
        errorDetails.put("status", HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
		
	}

}
