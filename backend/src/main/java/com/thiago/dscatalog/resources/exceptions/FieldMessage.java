package com.thiago.dscatalog.resources.exceptions;

import org.springframework.validation.FieldError;

public class FieldMessage {
	
	private String field;
	private String message;

	public FieldMessage() {
		super();
	}

	public FieldMessage(String field, String message) {
		super();
		this.field = field;
		this.message = message;
	}
	
	public FieldMessage(FieldError err) {
		super();
		this.field = err.getField();
		this.message = err.getDefaultMessage();
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
