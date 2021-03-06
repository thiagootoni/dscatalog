package com.thiago.dscatalog.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.FieldError;

public class ValidationError extends StandardError{

	List<FieldMessage> errors = new ArrayList<>();

	public ValidationError() {
		super();
	}

	public ValidationError(List<FieldError> list) {
		super();
		fillErrors(list);
	}


	public void fillErrors(List<FieldError> list) {
		for (FieldError err : list) {			
			errors.add(new FieldMessage(err));
		}
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}	
	
}
