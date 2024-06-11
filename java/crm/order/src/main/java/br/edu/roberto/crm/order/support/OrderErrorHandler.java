package br.edu.roberto.crm.order.support;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.edu.roberto.crm.support.EntityNotFoundException;
import br.edu.roberto.crm.support.StandardError;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class OrderErrorHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<StandardError> handleObjectNotFound(EntityNotFoundException ex, HttpServletRequest req) {
		StandardError error = new StandardError(ex.getMessage(), HttpStatus.NOT_FOUND.value(), new Date());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(OrderException.class)
	public ResponseEntity<StandardError> handleObjectNotFound(OrderException ex, HttpServletRequest req) {
		StandardError error = new StandardError(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), new Date());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
}
