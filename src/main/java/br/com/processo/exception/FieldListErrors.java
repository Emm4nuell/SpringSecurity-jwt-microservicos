package br.com.processo.exception;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;

public class FieldListErrors extends ErroException {

	@Getter
	private List<FieldErrors> errors;

	public FieldListErrors(LocalDateTime timesTamp, Integer Status, String error, String message, String path) {
		super(timesTamp, Status, error, message, path);
		// TODO Auto-generated constructor stub
	}

	public FieldListErrors(LocalDateTime timesTamp, Integer Status, String error, String message, String path,
			List<FieldErrors> errors) {
		super(timesTamp, Status, error, message, path);
		this.errors = errors;
	}
	
	

}
