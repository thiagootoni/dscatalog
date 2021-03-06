package com.thiago.dscatalog.services.validations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.thiago.dscatalog.dto.NewUserDTO;
import com.thiago.dscatalog.entities.User;
import com.thiago.dscatalog.repositories.UserRepository;
import com.thiago.dscatalog.resources.exceptions.FieldMessage;

public class NewUserValidator implements ConstraintValidator<NewUserValid, NewUserDTO> {
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public void initialize(NewUserValid ann) {
	}

	@Override
	public boolean isValid(NewUserDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		User user = this.userRepository.findByEmail(dto.getEmail());
		
		// Coloque aqui seus testes de validação, acrescentando objetos FieldMessage à lista
		
		if (user != null) {
			list.add(new FieldMessage("email", "Email already exists"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getField())
					.addConstraintViolation();
		}
		
		return list.isEmpty();
	}
}
