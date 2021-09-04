package com.bridgelabz.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bridgelabz.userservice.response.Response;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(FundooException.class)
	public ResponseEntity<Response> handleFundooException(FundooException ex) {
		
		Response error = new Response(ex.getStatusCode(), ex.getStatusMessage(), null);
		return new ResponseEntity<Response>(error, HttpStatus.BAD_GATEWAY);
	}
	
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<Response> handleExpiredJwtException(ExpiredJwtException ex) {
		
		Response error = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Token is expired", null);
		return new ResponseEntity<Response>(error, HttpStatus.BAD_GATEWAY);
	}

	
}
