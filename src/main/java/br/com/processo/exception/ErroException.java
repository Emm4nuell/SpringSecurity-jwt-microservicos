package br.com.processo.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErroException {

	private LocalDateTime timesTamp;
	private Integer Status;
	private String error;
	private String message;
	private String path;
}
