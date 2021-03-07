package com.thiago.dscatalog.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.thiago.dscatalog.services.validations.UserInsertValid;

@UserInsertValid
public class UserInsertDTO extends UserDTO{
	private static final long serialVersionUID = 1L;
	
	@NotBlank
	@Size(min = 3, message = "Mínimo de 3 caractéres")
	private String password;

	public UserInsertDTO(String password) {
		super();
		this.password = password;
	}

	public UserInsertDTO() {
		super();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
