package br.com.processo.handler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.processo.exception.ErroException;
import br.com.processo.exception.FieldErrors;
import br.com.processo.exception.FieldListErrors;

@RestControllerAdvice
public class HandlerException {

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErroException> noSuchElementException(NoSuchElementException exception) {
		ErroException error = new ErroException(LocalDateTime.now(),
				HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", "Usuario não localizado",
				exception.getClass().toString());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<FieldListErrors> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
		List<FieldError> error = exception.getBindingResult().getFieldErrors();
		List<FieldErrors> errors = new ArrayList<FieldErrors>();
		for (FieldError fieldError : error) {
			errors.add(new FieldErrors(fieldError.getField(), fieldError.getDefaultMessage()));
		}
		
		FieldListErrors lista = new FieldListErrors(
				LocalDateTime.now(), 
				HttpStatus.BAD_REQUEST.value(), 
				"Bad Request", 
				"Validation error!", 
				exception.getClass().toString(),
				errors);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(lista);
	}
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<ErroException> sQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException exception) {
		ErroException error = new ErroException(LocalDateTime.now(),
				HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", "Usuário já existe!",
				exception.getClass().toString());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}
