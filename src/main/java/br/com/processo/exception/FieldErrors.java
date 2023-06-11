package br.com.processo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FieldErrors {

	private String field;
	private String message;

}
