package com.thiago.dscatalog.dto;

import com.thiago.dscatalog.services.validations.UserUpdateValid;

@UserUpdateValid
public class UserUpdateDTO extends UserDTO{
	private static final long serialVersionUID = 1L;
	
	private String passord;

	public UserUpdateDTO() {
		super();
	}

	public UserUpdateDTO(String passord) {
		super();
		this.passord = passord;
	}

	public String getPassord() {
		return passord;
	}

	public void setPassord(String passord) {
		this.passord = passord;
	}

}
