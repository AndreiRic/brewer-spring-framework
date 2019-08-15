package com.algaworks.brewer.service.exception;

public class SenhaUsuarioObrigatoriaException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public SenhaUsuarioObrigatoriaException(String message) {
		super(message);
	}
	
}
