package com.cms.exception; 





import java.time.LocalDate;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice  //this class provides centralised exception handling for all the controllers in the application.
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


	@ExceptionHandler(value = {Exception.class})
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		ExceptionResponse errorDetails = new ExceptionResponse(LocalDate.now(), ex.getMessage(), request.getDescription(false),"HTTP 500");
		return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);   
	}

	@ExceptionHandler(value = {CourseInvalidException.class})
	public final ResponseEntity<ExceptionResponse> handleCourseInvalidException(CourseInvalidException ex, WebRequest request) {
		ExceptionResponse errorDetails = new ExceptionResponse(LocalDate.now(), ex.getMessage(), request.getDescription(false),String.valueOf(HttpStatus.NOT_FOUND));
		return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
	}	
	
		



}


