package com.lms.app.config;

import java.time.LocalDateTime;

import com.lms.app.value.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class LmsExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { UserNotFoundException.class, IncorrectEmailIdOrPasswordException.class,
			PageNotAvailableException.class , InvalidPayloadException.class})

	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		if (ex instanceof UserNotFoundException) {
			exceptionDetails.setStatus(HttpStatus.UNAUTHORIZED);
			exceptionDetails.setMessage("The username or password you entered is incorrect.");
			exceptionDetails.setTimestamp(LocalDateTime.now());
			return new ResponseEntity<Object>(exceptionDetails, HttpStatus.UNAUTHORIZED);
		} else if (ex instanceof IncorrectEmailIdOrPasswordException) {
			exceptionDetails.setStatus(HttpStatus.UNAUTHORIZED);
			exceptionDetails.setMessage("The current password that you have entered is incorrect.");
			exceptionDetails.setTimestamp(LocalDateTime.now());
			return new ResponseEntity<Object>(exceptionDetails, HttpStatus.UNAUTHORIZED);
		} else if (ex instanceof PageNotAvailableException) {
			exceptionDetails.setStatus(HttpStatus.NO_CONTENT);
			exceptionDetails.setMessage("No record found.");
			exceptionDetails.setTimestamp(LocalDateTime.now());
			return new ResponseEntity<Object>(exceptionDetails, HttpStatus.NO_CONTENT);
		}else if (ex instanceof InvalidPayloadException) {
			exceptionDetails.setStatus(HttpStatus.FORBIDDEN);
			exceptionDetails.setMessage(ex.getMessage());
			exceptionDetails.setTimestamp(LocalDateTime.now());
			return new ResponseEntity<Object>(exceptionDetails, HttpStatus.FORBIDDEN);
		} else {
			logger.info(ex);
			return ResponseEntity.status(500).body("Something went wrong.");
		}
	}

}
